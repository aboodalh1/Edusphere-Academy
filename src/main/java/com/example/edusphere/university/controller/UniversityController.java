package com.example.edusphere.university.controller;

import com.example.edusphere.university.model.University;
import com.example.edusphere.university.request.UniversiyRequest;
import com.example.edusphere.university.response.UniversityResponse;
import com.example.edusphere.university.service.UniversityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/universities")
public class UniversityController {

    private final UniversityService universityService;

    public UniversityController(UniversityService universityService) {
        this.universityService = universityService;
    }

    @Operation(
            summary = "Get All Universities"
    )
    @GetMapping
    public List<UniversityResponse> getAllUniversities() {
        return universityService.getAllUniversities();
    }

    @Operation(
            summary = "Get University by ID"
    )
    @GetMapping("/{id}")
    public ResponseEntity<UniversityResponse> getUniversityById(@PathVariable Long id) {
        return ResponseEntity.ok(universityService.getUniversityById(id));
    }

//    @PreAuthorize("hasRole('ADMIN')")
@Operation(
        summary = "Add new University",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                required = true,
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = UniversiyRequest.class),
                        examples = @ExampleObject(
                                value = "{ \"name\": \"Damascus university\" }"
                        )
                )
        )
)
    @PostMapping
    public ResponseEntity<UniversityResponse> createUniversity(@RequestBody UniversiyRequest university) {
        return ResponseEntity.ok(universityService.createUniversity(university));
    }


//    @PreAuthorize("hasRole('ADMIN')")
@Operation(
        summary = "Edit University",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                required = true,
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = UniversiyRequest.class),
                        examples = @ExampleObject(
                                value = "{ \"name\": \"Damascus university\"}"
                        )
                )
        )
)
    @PutMapping("/{id}")
    public ResponseEntity<University> updateUniversity(@PathVariable Long id, @RequestBody UniversiyRequest university) {
        return universityService.updateUniversity(id, university)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Delete University"
    )
//    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUniversity(@PathVariable Long id) {
        universityService.deleteUniversity(id);
        return ResponseEntity.noContent().build();
    }
}