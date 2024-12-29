package com.mohammad.relief.data.dto.response;

import java.time.LocalDate;

public record CheckInResponseDto(
        LocalDate date,
        String status,
        Integer streak
) {
}
