package com.example.edusphere.course.repository;

import com.example.edusphere.course.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course,Long> {
}
