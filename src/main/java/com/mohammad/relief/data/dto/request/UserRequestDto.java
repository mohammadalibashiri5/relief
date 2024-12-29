package com.mohammad.relief.data.dto.request;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record UserRequestDto(
        @NotNull
        @NotBlank
        String name,
        @NotNull
        @NotBlank
        String familyName,
        @NotNull
        String username,
        @NotNull
        @Email
        String email,
        @NotNull
        @Pattern(regexp = "^[A-Z][A-Za-z\\d@$!%*?&]{7,15}$")
        String password,
        LocalDate dateOfBirth) { }

