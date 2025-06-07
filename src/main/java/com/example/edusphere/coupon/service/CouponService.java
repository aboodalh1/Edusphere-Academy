package com.example.edusphere.coupon.service;

import com.example.edusphere.coupon.Coupon;
import com.example.edusphere.coupon.respository.CouponRepository;
import com.example.edusphere.student.model.Student;
import com.example.edusphere.student.repository.StudentRepository;
import com.example.edusphere.wallet.Wallet;
import com.example.edusphere.wallet.repository.WalletRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CouponService {

    private final CouponRepository couponRepository;
    private final StudentRepository studentRepository;
    private final WalletRepository walletRepository;

    public CouponService(CouponRepository couponRepository, StudentRepository studentRepository, WalletRepository walletRepository) {
        this.couponRepository = couponRepository;
        this.studentRepository = studentRepository;
        this.walletRepository = walletRepository;
    }

    public Coupon createCoupon(Coupon coupon) {
        return couponRepository.save(coupon);
    }

    public void applyCoupon(String code, UUID studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Coupon coupon = couponRepository.findByCode(code)
                .orElseThrow(() -> new RuntimeException("Coupon not found"));

        if (!coupon.isReusable() && coupon.getUsedByStudents().contains(student)) {
            throw new RuntimeException("Coupon already used by this student");
        }

        Wallet wallet = student.getWallet();
        if (wallet == null) {
            wallet = new Wallet();
            wallet.setStudent(student);
            wallet.setBalance(0.0);
        }

        wallet.setBalance(wallet.getBalance() + coupon.getBonusAmount());
        coupon.getUsedByStudents().add(student);

        walletRepository.save(wallet);
        couponRepository.save(coupon);
    }
}