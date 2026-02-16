package com.mohammad.relief.features.userSide.userTrigger.data;

import com.mohammad.relief.features.userSide.userTrigger.domain.UserTrigger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TriggerRepository extends JpaRepository<UserTrigger, Long> {
}
