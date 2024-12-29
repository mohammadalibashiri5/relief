package com.mohammad.relief.data.dto.response;

public record TriggerResponseDTO(
        String triggerName,
        String userName,
        String addictionName,
        String triggerType,
        String triggerDescription,
        String avoidanceStrategy

) {
}