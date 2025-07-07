package com.mohammad.relief.repository;

import com.mohammad.relief.data.entity.UserAddiction;
import com.mohammad.relief.data.entity.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserAddictionRepository extends JpaRepository<UserAddiction, Integer> {
    UserAddiction getAdminAddictionById(Long addictionId);

    List<UserAddiction> findByUser(Visitor user);
}
