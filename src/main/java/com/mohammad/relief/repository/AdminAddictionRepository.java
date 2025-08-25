package com.mohammad.relief.repository;

import com.mohammad.relief.data.entity.addiction.AdminAddiction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdminAddictionRepository extends JpaRepository<AdminAddiction, Long> {
    List<AdminAddiction> findByCategoryType_Name(String categoryTypeName);

    Optional<AdminAddiction> findByName(String addictionName);
}
