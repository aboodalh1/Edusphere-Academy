package com.example.edusphere.feedback.service;

import com.example.edusphere.config.EmailService;
import com.example.edusphere.feedback.model.Feedback;
import com.example.edusphere.feedback.repository.FeedbackRepository;
import com.example.edusphere.feedback.request.FeedbackRequest;
import com.example.edusphere.feedback.response.FeedbackResponse;
import com.example.edusphere.student.model.Student;
import com.example.edusphere.student.repository.StudentRepository;
import com.example.edusphere.util.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final StudentRepository studentRepository;
    private  final EmailService emailService;
    public FeedbackService(FeedbackRepository feedbackRepository, StudentRepository studentRepository, EmailService emailService) {
        this.feedbackRepository = feedbackRepository;
        this.studentRepository = studentRepository;
        this.emailService = emailService;
    }

    public FeedbackResponse createFeedback(FeedbackRequest request) {
        Student student = studentRepository.findById(request.getSudentId())
                .orElseThrow(() -> new NotFoundException("User not found"));

        Feedback feedback = new Feedback();
        feedback.setMessage(request.getMessage());
        feedback.setUser(student);

        Feedback saved = feedbackRepository.save(feedback);

        FeedbackResponse response = new FeedbackResponse();
        response.setId(saved.getId());
        response.setMessage(saved.getMessage());
        response.setCreatedAt(saved.getCreatedAt());
        response.setUserName(student.getUsername());

            emailService.sendMail("mulhamzak@gmail.com","Edusphere First Email","Hey Mulham, how are you my friend, it's Edusphere first test e mail sent from Back-End \n \n \n Best regards \n AbdAllah");

        return response;
    }

    public List<FeedbackResponse> getFeedbacksByUser(UUID studentId) {
        return feedbackRepository.findByStudentId(studentId).stream().map(feedback -> {
            FeedbackResponse response = new FeedbackResponse();
            response.setId(feedback.getId());
            response.setMessage(feedback.getMessage());
            response.setCreatedAt(feedback.getCreatedAt());
            response.setUserName(feedback.getUser().getUsername());
            return response;
        }).collect(Collectors.toList());
    }
}