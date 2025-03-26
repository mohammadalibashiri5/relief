package com.mohammad.relief.data.dto.response;

import com.mohammad.relief.data.entity.enums.StreakLevel;

import java.time.LocalDate;

public record CheckInResponseDto(
        String userName,
        String addictionName,
        LocalDate startDate,
        Integer currentStreak,
        Integer longestStreak,
        LocalDate lastCheckinDate,
        Enum<StreakLevel> level
) {
}
