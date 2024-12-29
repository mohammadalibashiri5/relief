package com.mohammad.relief.repository;

import com.mohammad.relief.data.entity.Trigger;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TriggerRepository extends JpaRepository<Trigger, Integer> {
    Optional<Trigger> findByTriggerName(String triggerName);
}
