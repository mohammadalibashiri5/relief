package com.mohammad.relief.data.dto;

import java.time.LocalDate;

public record CheckInResponseDto(
        LocalDate date,
        String status,
        Integer streak
) {
}
