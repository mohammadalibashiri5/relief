package com.mohammad.relief.controller;

import com.mohammad.relief.data.entity.SolutionFeedback;
import com.mohammad.relief.service.FeedBackService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
@RestController
@RequestMapping("/api/v1/feedback")
@RequiredArgsConstructor
public class FeedBackController {

    private final FeedBackService feedbackService;

    @PostMapping("/{solutionId}/feedback")
    public ResponseEntity<SolutionFeedback> giveFeedback(
            @PathVariable Long solutionId,
            Principal principal,
            @RequestParam boolean efficient) {
        String email = principal.getName();
        SolutionFeedback feedback = feedbackService.giveFeedback(solutionId, email, efficient);
        return ResponseEntity.ok(feedback);
    }

    @GetMapping("/{solutionId}/efficiency")
    public ResponseEntity<Double> getEfficiencyRate(@PathVariable Long solutionId) {
        double rate = feedbackService.getEfficiencyRate(solutionId);
        return ResponseEntity.ok(rate);
    }
}
