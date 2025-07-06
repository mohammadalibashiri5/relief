package com.mohammad.relief.repository;

import com.mohammad.relief.data.entity.Admin;
import com.mohammad.relief.data.entity.CategoryType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryTypeRepository extends JpaRepository<CategoryType, Integer> {
    void deleteByAdminAndId(Admin admin, int id);

    List<CategoryType> findAllByAdmin(Admin admin);
}
