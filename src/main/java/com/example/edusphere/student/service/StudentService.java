package com.example.edusphere.student.service;

import com.example.edusphere.config.JwtService;
import com.example.edusphere.config.RateLimiterConfig;
import com.example.edusphere.student.model.Student;
import com.example.edusphere.student.repository.StudentRepository;
import com.example.edusphere.student.request.LoginRequest;
import com.example.edusphere.student.request.RegisterStudentRequest;
import com.example.edusphere.student.response.StudentAuthResponse;
import com.example.edusphere.university.request.RegenerateCodeRequest;
import com.example.edusphere.util.exception.InvalidCredentialsException;
import com.example.edusphere.util.exception.RequestNotValidException;
import com.example.edusphere.util.exception.TooManyRequestException;
import com.example.edusphere.util.exception.UnAuthorizedException;
import com.example.edusphere.verification.model.VerificationCode;
import com.example.edusphere.verification.repository.VerificationRepository;
import com.example.edusphere.verification.request.VerificationRequest;
import com.example.edusphere.verification.response.VerificationResponse;
import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class StudentService implements UserDetailsService {

    private final StudentRepository studentRepository;
    private final VerificationRepository verificationRepository;
    private static final String LOGIN_RATE_LIMITER = "loginRateLimiter";
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RateLimiterConfig rateLimiterConfig;
    private final RateLimiterRegistry rateLimiterRegistry;
    private final int minutesToExpired = 2;
    Student globalStudent;

    public StudentService(StudentRepository studentRepository, VerificationRepository verificationRepository, JwtService jwtService, AuthenticationManager authenticationManager, RateLimiterConfig rateLimiterConfig, RateLimiterRegistry rateLimiterRegistry) {
        this.studentRepository = studentRepository;
        this.verificationRepository = verificationRepository;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.rateLimiterConfig = rateLimiterConfig;
        this.rateLimiterRegistry = rateLimiterRegistry;
    }

    @Transactional
    public VerificationResponse studentRegister(RegisterStudentRequest request) {
        Student existedPhoneNumber = studentRepository.findByPhoneNumber(request.getPhoneNumber()).orElse(null);
        if (existedPhoneNumber != null) {
            throw new RequestNotValidException("Phone number already exists");
        }
        globalStudent = new Student();
        globalStudent.setBirthDate(request.getBirthDate());
        globalStudent.setFirstName(request.getFirstName());
        globalStudent.setLastName(request.getLastName());
        globalStudent.setPhoneNumber(request.getPhoneNumber());
        studentRepository.save(globalStudent);

        String otpCode = generateOtp();
        return createOTPCode(request.getPhoneNumber(), otpCode, LocalDateTime.now().plusMinutes(minutesToExpired));
    }

    private VerificationResponse createOTPCode(String phoneNumber, String otpCode, LocalDateTime localDateTime) {
        VerificationCode code = new VerificationCode();
        code.setPhoneNumber(phoneNumber);
        code.setOtp(otpCode);
        code.setExpiryTime(localDateTime);
        verificationRepository.save(code);
        VerificationResponse response = new VerificationResponse();
        response.setOTP(code.getOtp());
        response.setExpiredDate(code.getExpiryTime());
        return response;
    }

    public StudentAuthResponse verifyCode(VerificationRequest request) {
        VerificationCode code = verificationRepository.findVerificationCodeByPhoneNumber(request.getPhoneNumber()).orElseThrow(
                () -> new UsernameNotFoundException("Invalid phone number")
        );
        if (LocalDateTime.now().isAfter(code.getExpiryTime())) {
            throw new UnAuthorizedException("OTP code has expired");
        } else if (!code.getOtp().equals(request.getOTP())) {
            throw new UnAuthorizedException("Invalid OTP code");
        } else {
            var jwtToken = jwtService.generateToken(globalStudent);
            StudentAuthResponse studentAuthResponse = new StudentAuthResponse();
            studentAuthResponse.setToken(jwtToken);
            studentAuthResponse.setFirstName(globalStudent.getFirstName());
            studentAuthResponse.setLastName(globalStudent.getLastName());
            studentAuthResponse.setPhoneNumber(globalStudent.getPhoneNumber());
            studentAuthResponse.setId(globalStudent.getId());
            return studentAuthResponse;
        }
    }

    public VerificationResponse regenerateCode(RegenerateCodeRequest request) {
        VerificationCode verificationCode = verificationRepository.findVerificationCodeByPhoneNumber(request.getPhoneNumber()).orElseThrow(
                () -> new UnAuthorizedException("Invalid phone number")
        );
        verificationCode.setOtp(generateOtp());
        verificationRepository.save(verificationCode);
        VerificationResponse verificationResponse= new VerificationResponse();
        verificationResponse.setExpiredDate(verificationCode.getExpiryTime());
        verificationResponse.setOTP(verificationCode.getOtp());
        return verificationResponse;
    }

    public void logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null)
        {
            SecurityContextHolder.getContext().setAuthentication(null);
        }
        }



    @Transactional
    public StudentAuthResponse studentLogin(LoginRequest request, HttpServletRequest httpServletRequest) {

        String userIp = httpServletRequest.getRemoteAddr();
        if (rateLimiterConfig.getBlockedIPs().contains(userIp)) {
            throw new TooManyRequestException("Rate limit reached for login attempts. try again later.");
        }

        String rateLimiterKey = LOGIN_RATE_LIMITER + "-" + userIp;
        RateLimiter rateLimiter = rateLimiterRegistry.rateLimiter(rateLimiterKey);

        if (rateLimiter.acquirePermission()) {

            Student student = studentRepository.findByPhoneNumber(request.getPhoneNumber()).orElseThrow(
                    () -> new RequestNotValidException("Phone number not found")
            );
            VerificationCode code = verificationRepository.findVerificationCodeByPhoneNumber(student.getPhoneNumber()).orElseThrow(
                    ()->new UnAuthorizedException("Phone number didn't exit")
            );

            if(!code.getOtp().equals(request.getOTP())){
                throw new UnAuthorizedException("Invalid OTP");
            }

            if(code.getExpiryTime().isBefore(LocalDateTime.now())){
                throw new UnAuthorizedException("Expired OTP");
            }

            var jwtToken = jwtService.generateToken(student);
            StudentAuthResponse response = new StudentAuthResponse();
            response.setToken(jwtToken);
            response.setLastName(student.getLastName());
            response.setFirstName(student.getFirstName());
            response.setPhoneNumber(student.getPhoneNumber());
            response.setId(student.getId());
            return response;
        } else
            rateLimiterConfig.blockIP(userIp);
        throw new TooManyRequestException("Rate limit reached for login attemps. try again later.");
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }


    public String generateOtp() {
        return String.valueOf(new Random().nextInt(899999) + 100000); // 6-digit OTP
    }


}
