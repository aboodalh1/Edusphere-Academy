package com.example.edusphere.course.service;

import com.example.edusphere.college.model.College;
import com.example.edusphere.college.repository.CollegeRepository;
import com.example.edusphere.course.Course;
import com.example.edusphere.course.repository.CourseRepository;
import com.example.edusphere.course.request.CourseRequest;
import com.example.edusphere.course.response.CourseResponse;
import com.example.edusphere.util.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final CollegeRepository collegeRepository;

    public CourseService(CourseRepository courseRepository, CollegeRepository collegeRepository) {
        this.courseRepository = courseRepository;
        this.collegeRepository = collegeRepository;
    }

    public CourseResponse createCourse(CourseRequest request) {
        College college = collegeRepository.findById(request.getCollegeId())
                .orElseThrow(() -> new NotFoundException("College not found"));

        Course course = new Course();
        course.setName(request.getName());
        course.setCollege(college);
        course.setDescription(request.getDescription());
        return mapToResponse(courseRepository.save(course));
    }

    public List<CourseResponse> getAllCourses() {
        return courseRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public Optional<CourseResponse> getCourseById(Long id) {
        return courseRepository.findById(id)
                .map(this::mapToResponse);
    }

    public Optional<CourseResponse> updateCourse(Long id, CourseRequest request) {
        return courseRepository.findById(id).map(course -> {
            College college = collegeRepository.findById(request.getCollegeId())
                    .orElseThrow(() -> new NotFoundException("College not found"));

            course.setName(request.getName());
            course.setCollege(college);
            course.setDescription(request.getDescription());

            return mapToResponse(courseRepository.save(course));
        });
    }

    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }

    private CourseResponse mapToResponse(Course course) {
        CourseResponse response = new CourseResponse();
        response.setId(course.getId());
        response.setName(course.getName());
        if (course.getCollege() != null) {
            response.setCollegeId(course.getCollege().getId());
            response.setCollegeName(course.getCollege().getName());
        }
        return response;
    }
}
