package com.mohammad.relief.repository;

import com.mohammad.relief.data.entity.Addiction;
import com.mohammad.relief.data.entity.Trigger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface TriggerRepository extends JpaRepository<Trigger, Integer> {
    Optional<Trigger> findByName(String triggerName);
    Optional<Trigger> findById(Long id);
    List<Trigger> findByAddiction(Addiction addiction);
}
