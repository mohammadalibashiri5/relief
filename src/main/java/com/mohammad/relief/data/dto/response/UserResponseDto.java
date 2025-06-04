package com.mohammad.relief.data.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


public record UserResponseDto(
        String name,
        String familyName,
        String username,
        String email,
        LocalDateTime createdAt,
        LocalDate dateOfBirth,
        List<AddictionResponseDto> addictions) {}
