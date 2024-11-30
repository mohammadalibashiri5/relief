package com.mohammad.relief.data.dto;

import com.mohammad.relief.data.entity.enums.Severity;

public record AddictionResponseDto(
        String name,
        String description,
        Severity severityLevel,
        Integer yearOfAddiction
) {
}
