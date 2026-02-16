package com.mohammad.relief.service;

import com.mohammad.relief.data.entity.Journey;
import com.mohammad.relief.data.entity.enums.Milestone;
import com.mohammad.relief.mapper.JourneyMapper;
import com.mohammad.relief.repository.JourneyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class JourneyService {
    private final JourneyRepository journeyRepository;
    private final JourneyMapper journeyMapper;
    private final UserService userService;
    private final UserAddictionService userAddictionService;
    private final Clock clock;

//    public JourneyResponseDto startJourney(JourneyReqDto req, Long userAddictionId, String email) throws ReliefApplicationException {
//        UserAddiction ua = userAddictionService.findAddictionByIdAndValidateOwnership(userAddictionId, email);
//
//        boolean exists = journeyRepository.existsByUserAddictionAndCancelledFalse(ua);
//        if (exists) {
//            throw new IllegalStateException("Journey already in progress");
//        }
//        LocalDate today = LocalDate.now(clock);
//
//        Journey journey = journeyMapper.toEntity(req);
//        journey.setUserAddiction(ua);
//        journey.setStarted(true);
//        journey.setStartTime(today);
//        journey.setMilestone(calculateCurrentMilestone(journey));
//        journey.setCancelled(false);
//
//        return journeyMapper.toResponseDto(journeyRepository.save(journey));
//    }

    public Milestone calculateCurrentMilestone(Journey journey) {
        if (journey.getStartTime() == null || journey.isCancelled()) {
            return null;
        }
        LocalDate today = LocalDate.now(clock);
        long daysActive = ChronoUnit.DAYS.between(journey.getStartTime(), today);

        if (daysActive == 0) {
            return Milestone.ZERO;
        }

        return Milestone.getLatestAchieved(daysActive);
    }
//    public List<JourneyResponseDto> getJourneys(Long userAddictionId, String email) throws ReliefApplicationException {
//        UserAddiction ua = userAddictionService.findAddictionByIdAndValidateOwnership(userAddictionId, email);
//
//        if (ua == null) {
//            throw new ReliefApplicationException("Addiction not found");
//        }
//        List<Journey> journeys = journeyRepository.findByUserAddiction((ua));
//        return journeys
//                .stream()
//                .map(journeyMapper::toResponseDto)
//                .toList();
//    }

}
