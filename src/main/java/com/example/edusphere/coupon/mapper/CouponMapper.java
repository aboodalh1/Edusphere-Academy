package com.example.edusphere.coupon.mapper;

import com.example.edusphere.coupon.Coupon;
import com.example.edusphere.coupon.request.CouponRequest;
import com.example.edusphere.coupon.response.CouponResponse;
import com.example.edusphere.student.model.Student;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class CouponMapper {

    public static Coupon toEntity(CouponRequest request) {
        Coupon coupon = new Coupon();
        coupon.setCode(request.getCode());
        coupon.setBonusAmount(request.getBonusAmount());
        coupon.setReusable(request.isReusable());
        return coupon;
    }

    public static CouponResponse toResponse(Coupon coupon) {
        CouponResponse response = new CouponResponse();
        response.setId(coupon.getId());
        response.setCode(coupon.getCode());
        response.setBonusAmount(coupon.getBonusAmount());
        response.setReusable(coupon.isReusable());

        Set<UUID> usedIds = coupon.getUsedByStudents().stream()
                .map(Student::getId)
                .collect(Collectors.toSet());
        response.setUsedByStudentIds(usedIds);

        return response;
    }
}