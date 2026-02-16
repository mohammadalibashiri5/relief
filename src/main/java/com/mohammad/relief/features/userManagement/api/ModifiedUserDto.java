package com.mohammad.relief.features.userManagement.api;

import java.time.LocalDate;

public record ModifiedUserDto(
        String name,
        String familyName,
        String password,
        LocalDate dateOfBirth) {}
