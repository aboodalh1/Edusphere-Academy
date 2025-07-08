package com.example.edusphere.lecture.controller;


import com.example.edusphere.lecture.request.LectureRequest;
import com.example.edusphere.lecture.response.LectureResponse;
import com.example.edusphere.lecture.service.LectureService;
import com.example.edusphere.student.request.RegisterStudentRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @Operation(
            summary = "Add new Lecture",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = LectureRequest.class),
                            examples = @ExampleObject(
                                    value = "{ \"name\": \"Introduction to linear algebra\", \"description\": \"The description of the course goes here\", \"content\": \"the content goes here\" , \"courseId\": \"1\"}"
                            )
                    )
            )
    )
    @PostMapping
    public ResponseEntity<LectureResponse> create(@RequestBody LectureRequest request) {
        return ResponseEntity.ok(lectureService.createLecture(request));
    }
    @Operation(
            summary = "Get All Lectures"
    )
    @GetMapping
    public ResponseEntity<List<LectureResponse>> getAll() {
        return ResponseEntity.ok(lectureService.getAllLectures());
    }
    @Operation(
            summary = "Get lecture by ID"
    )
    @GetMapping("/{id}")
    public ResponseEntity<LectureResponse> getById(@PathVariable Long id) {
        return lectureService.getLectureById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @Operation(
            summary = "Edit Lecture",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = LectureRequest.class),
                            examples = @ExampleObject(
                                    value = "{ \"name\": \"Introduction to linear algebra\", \"description\": \"The description of the course goes here\", \"content\": \"the content goes here\" , \"courseId\": \"1\"}"
                            )
                    )
            )
    )
    @PutMapping("/{id}")
    public ResponseEntity<LectureResponse> update(@PathVariable Long id, @RequestBody LectureRequest request) {
        return lectureService.updateLecture(id, request)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @Operation(
            summary = "Delete Lecture"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        lectureService.deleteLecture(id);
        return ResponseEntity.noContent().build();
    }
}
