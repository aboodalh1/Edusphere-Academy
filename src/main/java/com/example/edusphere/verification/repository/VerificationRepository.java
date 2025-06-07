package com.example.edusphere.verification.repository;

import com.example.edusphere.verification.model.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VerificationRepository extends JpaRepository<VerificationCode,Long> {

    Optional<VerificationCode> findVerificationCodeByPhoneNumber(String phoneNumber);
}
