package com.mohammad.relief.data.dto.request;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record UserRequestDto(
        @NotNull
        @NotBlank
        @Size(min = 2, max = 30)
        String name,
        @NotNull
        @NotBlank
        @Size(min = 2, max = 30)
        String familyName,
        @NotNull
        @NotBlank
        @Size(min = 5, max = 30)
        String username,
        @NotNull
        @Email
        String email,
        @NotNull
        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*]).{8,16}$",
                message = "Password must contain at least one uppercase letter, " +
                        "one digit, one special character " +
                        "and be between 8 and 16 characters long."
        )
        String password,
        @Past
        LocalDate dateOfBirth) {
}

