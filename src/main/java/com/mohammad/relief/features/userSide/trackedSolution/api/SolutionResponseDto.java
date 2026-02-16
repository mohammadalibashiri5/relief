package com.mohammad.relief.features.userSide.trackedSolution.api;

import java.time.LocalDateTime;

public record SolutionResponseDto(
        String name,
        String description,
        LocalDateTime createdAt
) {
}
