package com.example.edusphere.college.request;

import jakarta.validation.constraints.NotBlank;

public class CollegeRequest {

    @NotBlank(message = "College name is mandatory")
    private String  collegeName;

    @NotBlank(message = "University id is required")
    private Long UniversityId;

    public String getCollegeName() {
        return collegeName;
    }

    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }

    public Long getUniversityId() {
        return UniversityId;
    }

    public void setUniversityId(Long universityId) {
        UniversityId = universityId;
    }
}
