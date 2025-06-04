package com.mohammad.relief.data.dto.response;


public record TriggerResponseDTO(
        Long id,
        String name,
        String addictionName,
        String description,
        int repetitionCount

) {
}