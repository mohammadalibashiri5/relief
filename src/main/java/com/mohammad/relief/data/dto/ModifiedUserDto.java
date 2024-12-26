package com.mohammad.relief.data.dto;

import java.time.LocalDate;

public record ModifiedUserDto(
        String name,
        String familyName,
        String password,
        LocalDate dateOfBirth) {}
