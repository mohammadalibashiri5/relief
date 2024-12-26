package com.mohammad.relief.repository;

import com.mohammad.relief.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Optional<User> findByRole(String role);
    Boolean existsNotByUsername(String username);
    Boolean existsNotByEmail(String email);
    Optional<User> findByEmailAndPassword(String email, String password);
    boolean existsByUsername(String username);
}
