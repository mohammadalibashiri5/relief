package com.mohammad.relief.features.adminSide.categoryTemplate.data;

import com.mohammad.relief.features.userManagement.domain.Admin;
import com.mohammad.relief.features.adminSide.categoryTemplate.domain.CategoryType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryTypeRepository extends JpaRepository<CategoryType, Integer> {

    Optional<CategoryType> findByNameAndAdmin(String name, Admin admin);
}
