package com.mohammad.relief.repository;

import com.mohammad.relief.data.entity.Visitor;
import com.mohammad.relief.data.entity.Visitor;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<Visitor, UUID> {
    Optional<Visitor> findByEmail(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
