package com.mohammad.relief.data.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record CheckInRequestDto(
        @NotNull @NotEmpty
        String userName,
        @NotNull @NotEmpty
        String addictionName,
        LocalDate startDate
) {
}
