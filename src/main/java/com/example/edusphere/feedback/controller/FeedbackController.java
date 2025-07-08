package com.example.edusphere.feedback.controller;

import com.example.edusphere.feedback.request.FeedbackRequest;
import com.example.edusphere.feedback.response.FeedbackResponse;
import com.example.edusphere.feedback.service.FeedbackService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/feedbacks")
public class FeedbackController {

    private final FeedbackService feedbackService;

    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @PostMapping
    public ResponseEntity<FeedbackResponse> sendFeedback(@RequestBody FeedbackRequest request) {
        return ResponseEntity.ok(feedbackService.createFeedback(request));
    }

    @GetMapping("/user/{studentId}")
    public ResponseEntity<List<FeedbackResponse>> getUserFeedbacks(@PathVariable UUID studentId) {
        return ResponseEntity.ok(feedbackService.getFeedbacksByUser(studentId));
    }
}