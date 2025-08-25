package com.mohammad.relief.data.dto.response;

import com.mohammad.relief.data.entity.enums.RoleType;

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
