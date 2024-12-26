package com.mohammad.relief.data.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record UserResponseDto(
        UUID id,
        String name,
        String familyName,
        String username,
        String email,
        LocalDateTime createdAt,
        LocalDate dateOfBirth) {}
