package com.mohammad.relief.repository;

import com.mohammad.relief.data.entity.Streak;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;
import java.util.UUID;
@Repository
public interface StreakRepository extends JpaRepository<Streak, Integer> {
    Optional<Streak> findByUserIdAndAddictionsId(UUID userId, Integer addictionId);
}
