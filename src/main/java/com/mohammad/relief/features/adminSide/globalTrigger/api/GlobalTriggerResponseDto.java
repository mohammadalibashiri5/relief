package com.mohammad.relief.features.adminSide.globalTrigger.api;

public record GlobalTriggerResponseDto(
        long id,
        String title,
        long adoptionCount,
        String addictionName
) {
}
