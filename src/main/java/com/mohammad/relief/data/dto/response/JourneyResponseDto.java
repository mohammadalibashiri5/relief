package com.mohammad.relief.data.dto.response;


public record JourneyResponseDto(
        int id,
        String userAddictionImageUrl,
        String userAddictionName,
        long daysActive,
        String milestoneLabel,
        String milestoneIcon,
        int daysToNextMilestone
) {
}
