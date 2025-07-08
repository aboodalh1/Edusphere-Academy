package com.example.edusphere.coupon.controller;

import com.example.edusphere.coupon.Coupon;
import com.example.edusphere.coupon.request.CouponRequest;
import com.example.edusphere.coupon.response.CouponResponse;
import com.example.edusphere.coupon.service.CouponService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/coupons")
public class CouponController {

    private final CouponService couponService;

    public CouponController(CouponService couponService) {
        this.couponService = couponService;
    }

    @PostMapping("/create")
    public ResponseEntity<CouponResponse> createCoupon(@RequestBody CouponRequest coupon) {
        return ResponseEntity.ok(couponService.createCoupon(coupon));
    }

    @PostMapping("/apply")
    public ResponseEntity<String> applyCoupon(
            @RequestParam String code,
            @RequestParam UUID studentId
    ) {
        couponService.applyCoupon(code, studentId);
        return ResponseEntity.ok("Coupon applied successfully");
    }
}
