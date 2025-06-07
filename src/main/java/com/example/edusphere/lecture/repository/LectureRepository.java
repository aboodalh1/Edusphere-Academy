package com.example.edusphere.lecture.repository;


import com.example.edusphere.lecture.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LectureRepository extends JpaRepository<Lecture, Long> {
}