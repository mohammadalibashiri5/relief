package com.mohammad.relief.features.userSide.userAddiction.api;


import com.mohammad.relief.features.userSide.motivation.api.MotivationResponseDto;

import java.time.LocalDateTime;
import java.util.List;

public record UserAddictionResponseDto(
        Long id,
        String addictionName,
        String addictionImageUrl,
        String categoryType,
        LocalDateTime journeyStartDate,
        int dayPerWeek,
        int frequencyPerDay,
        List<MotivationResponseDto> motivations
) {
}
