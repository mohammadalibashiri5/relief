package com.mohammad.relief.repository;

import com.mohammad.relief.data.entity.Solution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface SolutionRepository extends JpaRepository<Solution, Long> {
    Optional<Solution> findByName(String triggerName);
}
