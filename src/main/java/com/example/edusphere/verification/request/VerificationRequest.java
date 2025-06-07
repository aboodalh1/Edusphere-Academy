package com.example.edusphere.verification.request;

import jakarta.validation.constraints.NotBlank;

public class VerificationRequest {

    @NotBlank(message = "Phone Number required")
    private String  phoneNumber;
    @NotBlank(message = "OTP required")
    private String  OTP;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getOTP() {
        return OTP;
    }

    public void setOTP(String OTP) {
        this.OTP = OTP;
    }
}
