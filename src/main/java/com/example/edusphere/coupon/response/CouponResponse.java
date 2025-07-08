package com.example.edusphere.coupon.response;

import java.util.Set;
import java.util.UUID;

public class CouponResponse {
    private Long id;
    private String code;
    private double bonusAmount;
    private boolean reusable;
    private Set<UUID> usedByStudentIds;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public double getBonusAmount() {
        return bonusAmount;
    }

    public void setBonusAmount(double bonusAmount) {
        this.bonusAmount = bonusAmount;
    }

    public boolean isReusable() {
        return reusable;
    }

    public void setReusable(boolean reusable) {
        this.reusable = reusable;
    }

    public Set<UUID> getUsedByStudentIds() {
        return usedByStudentIds;
    }

    public void setUsedByStudentIds(Set<UUID> usedByStudentIds) {
        this.usedByStudentIds = usedByStudentIds;
    }
}
