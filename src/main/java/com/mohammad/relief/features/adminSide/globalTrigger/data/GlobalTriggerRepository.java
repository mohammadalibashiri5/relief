package com.mohammad.relief.features.adminSide.globalTrigger.data;

import com.mohammad.relief.features.adminSide.globalTrigger.domain.GlobalTrigger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GlobalTriggerRepository extends JpaRepository<GlobalTrigger, Long> {
    List<GlobalTrigger> findByAdminAddictionId(Long addictionId);
    @Modifying
    @Query(value = "UPDATE global_trigger gt " +
            "SET adoption_count = (" +
            "  SELECT COUNT(*) FROM user_trigger ut " +
            "  WHERE ut.name = gt.title" +
            ")", nativeQuery = true)
    void refreshAllAdoptionCounts();

    boolean existsByTitleIgnoreCase(String title);
}
