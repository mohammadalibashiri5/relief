package com.mohammad.relief.repository;

import com.mohammad.relief.data.entity.CheckIn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CheckInRepository extends JpaRepository<CheckIn, Integer> {
    Optional<CheckIn> findByUserIdAndDate(UUID userId, LocalDate date);

    List<CheckIn> findByUserId(UUID userId);

    Boolean existsByUserIdAndDate(UUID id, LocalDate yesterday);
}
