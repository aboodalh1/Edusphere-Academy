package com.example.edusphere.coupon;

import com.example.edusphere.student.model.Student;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String code;

    private double bonusAmount;

    // Optional: mark if it can be reused or not
    private boolean reusable = false;

    // Optional: track who used it
    @ManyToMany
    private Set<Student> usedByStudents = new HashSet<>();

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

    public Set<Student> getUsedByStudents() {
        return usedByStudents;
    }

    public void setUsedByStudents(Set<Student> usedByStudents) {
        this.usedByStudents = usedByStudents;
    }

    // getters and setters
}