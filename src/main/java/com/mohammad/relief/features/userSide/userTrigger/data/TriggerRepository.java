package com.mohammad.relief.features.userSide.userTrigger.data;

import com.mohammad.relief.features.userManagement.domain.Visitor;
import com.mohammad.relief.features.userSide.userTrigger.domain.UserTrigger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TriggerRepository extends JpaRepository<UserTrigger, Long> {
    List<UserTrigger> findAllByUser(Visitor user);
}
