package com.mohammad.relief.repository;

import com.mohammad.relief.data.entity.Trigger;
import com.mohammad.relief.data.entity.UserAddiction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface TriggerRepository extends JpaRepository<Trigger, Long> {
    Optional<Trigger> findByName(String triggerName);
    List<Trigger> findByUserAddiction(UserAddiction addiction);
}
