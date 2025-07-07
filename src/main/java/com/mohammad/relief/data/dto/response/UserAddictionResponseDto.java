package com.mohammad.relief.data.dto.response;

import com.mohammad.relief.data.entity.enums.Severity;

public record UserAddictionResponseDto(
        String addictionName,
        String categoryType,
        Severity severityLevel,
        Integer yearsOfAddiction
) {
}
