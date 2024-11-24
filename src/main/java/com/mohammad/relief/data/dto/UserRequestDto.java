package com.mohammad.relief.data.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record UserRequestDto(
        @NotNull
        String username,
        @NotNull
        @Email
        String email,
        @NotNull
        @Pattern(regexp = "^[A-Z][A-Za-z\\d@$!%*?&]{7,15}$")
        String password,
        LocalDate birthday) { }

