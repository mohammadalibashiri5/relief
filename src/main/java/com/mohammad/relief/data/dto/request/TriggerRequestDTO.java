package com.mohammad.relief.data.dto.request;

public record TriggerRequestDTO(
        String triggerName,
        String triggerType,
        String triggerDescription,
        String avoidanceStrategy

) {
}