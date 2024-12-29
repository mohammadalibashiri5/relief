package com.mohammad.relief.data.dto.request;


import com.mohammad.relief.data.entity.enums.Severity;


public record AddictionRequestDto(
        String name,
        String description,
        Severity severityLevel,
        Integer yearOfAddiction
) {
}
