package com.mohammad.relief.features.userManagement.api;

import com.mohammad.relief.features.userManagement.domain.RoleType;

import java.time.LocalDate;
import java.time.LocalDateTime;


public record UserResponseDto(
        String name,
        String familyName,
        String username,
        String email,
        LocalDateTime createdAt,
        LocalDate dateOfBirth,
        RoleType role
        ) {}
