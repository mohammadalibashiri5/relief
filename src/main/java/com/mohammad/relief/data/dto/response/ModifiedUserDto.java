package com.mohammad.relief.data.dto.response;

import java.time.LocalDate;

public record ModifiedUserDto(
        String name,
        String familyName,
        String password,
        LocalDate dateOfBirth) {}
