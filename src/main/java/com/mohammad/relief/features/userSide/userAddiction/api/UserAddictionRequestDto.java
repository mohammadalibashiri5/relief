package com.mohammad.relief.features.userSide.userAddiction.api;

import com.mohammad.relief.features.userSide.motivation.api.MotivationRequestDto;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record UserAddictionRequestDto(
        @NotNull
        @Min(1)@Max(30)
        int frequencyPerDay,
        @NotNull
        @Min(1)@Max(7)
        int daysPerWeek,
        @NotNull
        @NotEmpty
        List<MotivationRequestDto> motivations
) {
}
