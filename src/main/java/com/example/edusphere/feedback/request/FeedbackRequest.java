package com.example.edusphere.feedback.request;

import java.util.UUID;

public class FeedbackRequest {
    private UUID sudentId;
    private String message;

    public UUID getSudentId() {
        return sudentId;
    }

    public void setSudentId(UUID sudentId) {
        this.sudentId = sudentId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    // Getters and setters
}