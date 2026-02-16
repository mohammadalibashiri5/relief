package com.mohammad.relief.features.adminSide.aminAddiction.data;

import com.mohammad.relief.features.adminSide.aminAddiction.domain.AdminAddiction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdminAddictionRepository extends JpaRepository<AdminAddiction, Long> {
    List<AdminAddiction> findByCategoryType_Name(String categoryTypeName);

    Optional<AdminAddiction> findByName(String addictionName);
}
