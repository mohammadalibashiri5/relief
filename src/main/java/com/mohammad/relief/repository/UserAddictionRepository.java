package com.mohammad.relief.repository;

import com.mohammad.relief.data.entity.UserAddiction;
import com.mohammad.relief.data.entity.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserAddictionRepository extends JpaRepository<UserAddiction, Long> {
    List<UserAddiction> findByUser(Visitor user);

    Boolean existsByAddiction_NameAndUser(String addictionName, Visitor user);

    Optional<UserAddiction> findByIdAndUser(Long id, Visitor user);
}
