package com.mohammad.relief.data.dto.response;

import java.time.LocalDateTime;

public record SolutionResponseDto(
        String name,
        String description,
        LocalDateTime createdAt
) {
}
