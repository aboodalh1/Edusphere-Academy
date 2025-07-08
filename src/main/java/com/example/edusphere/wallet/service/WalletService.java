package com.example.edusphere.wallet.service;

import com.example.edusphere.config.JwtService;
import com.example.edusphere.student.model.Student;
import com.example.edusphere.student.repository.StudentRepository;
import com.example.edusphere.util.exception.NotFoundException;
import com.example.edusphere.util.exception.RequestNotValidException;
import com.example.edusphere.wallet.Wallet;
import com.example.edusphere.wallet.repository.WalletRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class WalletService {

    private final WalletRepository walletRepository;
    private final StudentRepository studentRepository;
    private  final JwtService jwtService;
    public WalletService(WalletRepository walletRepository, StudentRepository studentRepository, JwtService jwtService) {
        this.walletRepository = walletRepository;
        this.studentRepository = studentRepository;
        this.jwtService = jwtService;
    }

    public Wallet getWalletByStudentId(String header) {
        UUID studentId = jwtService.extractUserId(header);
        return walletRepository.findByStudentId(studentId)
                .orElseThrow(() -> new NotFoundException("Wallet not found for student ID " + studentId));
    }

    public Wallet createWalletForStudent(String header) {
        UUID studentId = jwtService.extractUserId(header);
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new NotFoundException("Student not found"));

        if (student.getWallet() != null) {
            throw new RequestNotValidException("Wallet already exists for this student.");
        }

        Wallet wallet = new Wallet();
        wallet.setStudent(student);
        wallet.setBalance(0.0);
        return walletRepository.save(wallet);
    }
}
