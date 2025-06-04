package com.mohammad.relief.service;

import com.mohammad.relief.data.entity.Solution;
import com.mohammad.relief.data.entity.SolutionFeedback;
import com.mohammad.relief.data.entity.Visitor;
import com.mohammad.relief.exception.ReliefApplicationException;
import com.mohammad.relief.repository.SolutionFeedbackRepository;
import com.mohammad.relief.repository.SolutionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedBackService {
    private final SolutionRepository solutionRepository;
    private final SolutionFeedbackRepository solutionFeedbackRepository;
    private final UserService userService;

    public SolutionFeedback giveFeedback(Long solutionId, String email, boolean efficient) {
        Solution solution = solutionRepository.findById(solutionId)
                .orElseThrow(() -> new RuntimeException("Solution not found"));

        // find the solution first by its id and then check if it exists in the database

        SolutionFeedback feedback = new SolutionFeedback();
        // initialize the feedback object with the given parameters
        feedback.setEfficient(efficient);
        feedback.setTimestamp(LocalDateTime.now());
        feedback.setSolution(solution);

        // You should fetch the user from UserRepository
        Visitor user;
        try {
            user = userService.findByEmail(email);
        } catch (ReliefApplicationException e) {
            throw new RuntimeException(e);
        }
        feedback.setUser(user);

        solutionFeedbackRepository.save(feedback);
        return feedback;
    }

    public double getEfficiencyRate(Long solutionId) {
        List<SolutionFeedback> feedbacks = solutionFeedbackRepository.findBySolutionId(solutionId);
        if (feedbacks.isEmpty()) return 0.0;
        long positive = feedbacks.stream().filter(SolutionFeedback::isEfficient).count();
        return (double) positive / feedbacks.size();
    }
}
