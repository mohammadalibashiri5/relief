package com.mohammad.relief.features.adminSide.globalTrigger.data;

import com.mohammad.relief.features.adminSide.globalTrigger.domain.GlobalTrigger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GlobalTriggerRepository extends JpaRepository<GlobalTrigger, Long> {
    List<GlobalTrigger> findByAdminAddictionId(Long addictionId);
}
