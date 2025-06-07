package com.example.edusphere.lecture.controller;


import com.example.edusphere.lecture.request.LectureRequest;
import com.example.edusphere.lecture.response.LectureResponse;
import com.example.edusphere.lecture.service.LectureService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lectures")
public class LectureController {

    private final LectureService lectureService;

    public LectureController(LectureService lectureService) {
        this.lectureService = lectureService;
    }

    @PostMapping
    public ResponseEntity<LectureResponse> create(@RequestBody LectureRequest request) {
        return ResponseEntity.ok(lectureService.createLecture(request));
    }

    @GetMapping
    public ResponseEntity<List<LectureResponse>> getAll() {
        return ResponseEntity.ok(lectureService.getAllLectures());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LectureResponse> getById(@PathVariable Long id) {
        return lectureService.getLectureById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<LectureResponse> update(@PathVariable Long id, @RequestBody LectureRequest request) {
        return lectureService.updateLecture(id, request)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        lectureService.deleteLecture(id);
        return ResponseEntity.noContent().build();
    }
}
