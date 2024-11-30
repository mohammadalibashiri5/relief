package com.mohammad.relief.data.dto;


import com.mohammad.relief.data.entity.enums.Severity;

import java.util.UUID;

public record AddictionRequestDto(
        UUID userId,
        String name,
        String description,
        Severity severityLevel,
        Integer yearOfAddiction
) {
}
