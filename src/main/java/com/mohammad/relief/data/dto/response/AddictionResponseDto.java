package com.mohammad.relief.data.dto.response;

import com.mohammad.relief.data.entity.enums.Severity;

public record AddictionResponseDto(
        Long id,
        String name,
        String description,
        Severity severityLevel,
        Integer yearOfAddiction
) {
}
