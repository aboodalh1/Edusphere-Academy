package com.example.edusphere.wallet.service;

import com.example.edusphere.student.model.Student;
import com.example.edusphere.student.repository.StudentRepository;
import com.example.edusphere.wallet.Wallet;
import com.example.edusphere.wallet.repository.WalletRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class WalletService {

    private final WalletRepository walletRepository;
    private final StudentRepository studentRepository;

    public WalletService(WalletRepository walletRepository, StudentRepository studentRepository) {
        this.walletRepository = walletRepository;
        this.studentRepository = studentRepository;
    }

    public Wallet getWalletByStudentId(UUID studentId) {
        return walletRepository.findByStudentId(studentId)
                .orElseThrow(() -> new RuntimeException("Wallet not found for student ID " + studentId));
    }

    public Wallet createWalletForStudent(UUID studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        if (student.getWallet() != null) {
            throw new RuntimeException("Wallet already exists for this student.");
        }

        Wallet wallet = new Wallet();
        wallet.setStudent(student);
        wallet.setBalance(0.0);
        return walletRepository.save(wallet);
    }
}
