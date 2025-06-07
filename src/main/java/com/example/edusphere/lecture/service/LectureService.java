package com.example.edusphere.lecture.service;

import com.example.edusphere.course.Course;
import com.example.edusphere.course.repository.CourseRepository;
import com.example.edusphere.lecture.Lecture;
import com.example.edusphere.lecture.repository.LectureRepository;
import com.example.edusphere.lecture.request.LectureRequest;
import com.example.edusphere.lecture.response.LectureResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LectureService {

    private final LectureRepository lectureRepository;
    private final CourseRepository courseRepository;

    public LectureService(LectureRepository lectureRepository, CourseRepository courseRepository) {
        this.lectureRepository = lectureRepository;
        this.courseRepository = courseRepository;
    }

    public LectureResponse createLecture(LectureRequest request) {
        Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new RuntimeException("Course not found"));

        Lecture lecture = new Lecture();
        lecture.setName(request.getName());
        lecture.setCourse(course);
        lecture.setContent(request.getContent());
        Lecture saved = lectureRepository.save(lecture);
        return mapToResponse(saved);
    }

    public List<LectureResponse> getAllLectures() {
        return lectureRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public Optional<LectureResponse> getLectureById(Long id) {
        return lectureRepository.findById(id)
                .map(this::mapToResponse);
    }

    public Optional<LectureResponse> updateLecture(Long id, LectureRequest request) {
        return lectureRepository.findById(id).map(existing -> {
            Course course = courseRepository.findById(request.getCourseId())
                    .orElseThrow(() -> new RuntimeException("Course not found"));
            existing.setContent(request.getContent());
            existing.setName(request.getName());
            existing.setCourse(course);
            return mapToResponse(lectureRepository.save(existing));
        });
    }

    public void deleteLecture(Long id) {
        lectureRepository.deleteById(id);
    }

    private LectureResponse mapToResponse(Lecture lecture) {
        LectureResponse response = new LectureResponse();
        response.setId(lecture.getId());
        response.setName(lecture.getName());
        response.setContent(lecture.getContent());
        response.setCourseId(lecture.getCourse().getId());
        response.setCourseName(lecture.getCourse().getName());
        return response;
    }
}
