package com.mohammad.relief.repository;

import com.mohammad.relief.data.entity.SolutionFeedback;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SolutionFeedbackRepository extends JpaRepository<SolutionFeedback, Long> {

    List<SolutionFeedback> findBySolutionId(Long solutionId);
}
