package com.mohammad.relief.service;

import com.mohammad.relief.data.entity.Journey;
import com.mohammad.relief.data.entity.enums.Milestone;
import com.mohammad.relief.mapper.JourneyMapper;
import com.mohammad.relief.repository.JourneyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JourneyServiceTest {

    @Mock
    private Clock clock; // Mock the clock to control time

    @Mock
    private JourneyRepository journeyRepository;
    @Mock
    private JourneyMapper journeyMapper;
    @Mock
    private UserService userService;
    @Mock
    private UserAddictionService userAddictionService;

    @InjectMocks
    private JourneyService journeyService;

    @Test
    void testMilestoneProgression() {
        // 1. Setup the "Current Time" (The day the user checks their progress)
        Instant futureInstant = Instant.parse("2026-02-05T10:00:00Z");
        ZoneId zoneId = ZoneId.of("UTC");

        // Tell the mock clock to always return this future date
        when(clock.instant()).thenReturn(futureInstant);
        when(clock.getZone()).thenReturn(zoneId);

        Journey journey = new Journey();
        journey.setStartTime(LocalDate.of(2025,2, 1));
        journey.setCancelled(false);

        // 3. Execute the real logic
        Milestone result = journeyService.calculateCurrentMilestone(journey);

        // 4. Assert
        assertNotNull(result, "Milestone should not be null");
        assertEquals(Milestone.ONE_YEAR, result);
    }

}