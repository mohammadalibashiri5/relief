package com.mohammad.relief.repository;

import com.mohammad.relief.data.entity.Visitor;
import com.mohammad.relief.data.entity.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<Visitor, UUID> {
    Optional<Visitor> findByUsername(String username);
    Optional<Visitor> findByEmail(String email);
    boolean existsByUsername(String username);
}
