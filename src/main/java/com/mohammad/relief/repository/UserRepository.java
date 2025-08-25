package com.mohammad.relief.repository;

import com.mohammad.relief.data.entity.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<Visitor, UUID> {
    Optional<Visitor> findByEmail(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
