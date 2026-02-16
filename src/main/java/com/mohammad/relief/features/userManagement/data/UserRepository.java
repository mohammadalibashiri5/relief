package com.mohammad.relief.features.userManagement.data;

import com.mohammad.relief.features.userManagement.domain.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<Visitor, UUID> {
    Optional<Visitor> findByEmail(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
