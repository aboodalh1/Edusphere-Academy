package com.example.edusphere.student.repository;

import com.example.edusphere.student.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface StudentRepository extends JpaRepository<Student, UUID> {
    Optional<Student> findByPhoneNumber(String phoneNumber);
}
