package com.mohammad.relief.repository;

import com.mohammad.relief.data.entity.Admin;
import com.mohammad.relief.data.entity.CategoryType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryTypeRepository extends JpaRepository<CategoryType, Integer> {

    Optional<CategoryType> findByNameAndAdmin(String name, Admin admin);
}
