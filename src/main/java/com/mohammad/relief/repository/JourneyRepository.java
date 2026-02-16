package com.mohammad.relief.repository;

import com.mohammad.relief.data.entity.Journey;
import com.mohammad.relief.data.entity.UserAddiction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JourneyRepository extends JpaRepository<Journey, Integer> {
    boolean existsByUserAddictionAndCancelledFalse(UserAddiction ua);

    List<Journey> findByUserAddiction(UserAddiction userAddiction);
}
