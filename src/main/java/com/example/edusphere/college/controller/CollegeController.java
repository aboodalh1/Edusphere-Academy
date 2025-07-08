package com.example.edusphere.college.controller;

import com.example.edusphere.college.model.College;
import com.example.edusphere.college.request.CollegeRequest;
import com.example.edusphere.college.service.CollegeService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/colleges")
public class CollegeController {

    private final CollegeService collegeService;

    public CollegeController(CollegeService collegeService) {
        this.collegeService = collegeService;
    }

    @Operation(summary = "Get all College")
    @GetMapping
    public List<College> getAllColleges() {
        return collegeService.getAllColleges();
    }

    @Operation(summary = "Get College by ID")
    @GetMapping("/{id}")
    public ResponseEntity<College> getCollegeById(@PathVariable Long id) {
        Optional<College> college = collegeService.getCollegeById(id);
        return college.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @Operation(summary = "Create New College")
//    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> createCollege(@RequestBody CollegeRequest college) {
        Optional<College> createdCollege = collegeService.createCollege(college);
        if (createdCollege.isPresent()) {
            return ResponseEntity.ok(createdCollege.get());
        } else {
            return ResponseEntity.badRequest().body("University not found");
        }
    }

    @Operation(summary = "Update College by ID")
//    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCollege(@PathVariable Long id,
                                           @RequestBody CollegeRequest college) {
        Optional<College> updatedCollege = collegeService.updateCollege(id, college);
        if (updatedCollege.isPresent()) {
            return ResponseEntity.ok(updatedCollege.get());
        } else {
            return ResponseEntity.badRequest().body("College or University not found");
        }
    }

    @Operation(summary = "Delete College by ID")
//    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCollege(@PathVariable Long id) {
        collegeService.deleteCollege(id);
        return ResponseEntity.noContent().build();
    }
}
