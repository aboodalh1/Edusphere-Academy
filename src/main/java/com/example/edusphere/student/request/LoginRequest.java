package com.example.edusphere.student.request;

import com.example.edusphere.util.annotation.ValidPhoneNumber;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.NumberFormat;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    public @NotBlank(message = "Email couldn't be blank") @Email(message = "Invalid Email Format") String getPhoneNumber() {
        return phoneNumber;
    }

    @NotBlank(message = "Email couldn't be blank")
    @ValidPhoneNumber
    private String phoneNumber;
    private String OTP;

    public @NotBlank(message = "Phone number couldn't be blank") String getEmail() {
        return phoneNumber;
    }

    public void setPhoneNumber(@NotBlank(message = "Phone number couldn't be blank") String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getOTP() {
        return OTP;
    }

    public void setOTP(String OTP) {
        this.OTP = OTP;
    }
}
