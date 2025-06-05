package com.mohammad.relief.data.dto.response;


import java.time.LocalDateTime;

public record TriggerResponseDTO(
        Long id,
        String name,
        String addictionName,
        String description,
        int repetitionCount,
        LocalDateTime createdAt

) {
}