package com.mohammad.relief.data.dto.request;

import com.mohammad.relief.data.entity.enums.Severity;
import jakarta.validation.constraints.Max;

public record UserAddictionRequestDto(
        Severity severityLevel,
        @Max(50)
        Integer yearsOfAddiction
) {
}
