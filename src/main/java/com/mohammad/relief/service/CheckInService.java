package com.mohammad.relief.service;

import com.mohammad.relief.data.entity.Addiction;
import com.mohammad.relief.data.entity.CheckIn;
import com.mohammad.relief.data.entity.Visitor;
import com.mohammad.relief.data.entity.enums.StreakLevel;
import com.mohammad.relief.mapper.CheckInMapper;
import com.mohammad.relief.repository.CheckInRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CheckInService {

    private final CheckInRepository checkInRepository;
    private final UserService userService;
    private final AddictionService addictionService;
    private final CheckInMapper checkInMapper;

//    public CheckInResponseDto register(String username, String addictionName, boolean isClean) throws ReliefApplicationException {
//
//        Visitor user = userService.findByEmail(username);
//
//        Addiction addiction = addictionService.getAddictionByName(addictionName);
//
//        if (isUserCheckedInToday(user, addiction)) {
//            throw new ReliefApplicationException("You have already checked in today.");
//        }
//
//        CheckIn checkIn = getOrCreateCheckIn(user, addiction);
//        updateCheckInStatus(checkIn, isClean);
//
//        CheckIn savedCheckIn = checkInRepository.save(checkIn);
//        return checkInMapper.toDto(savedCheckIn);
//    }

    private StreakLevel getLevel(int streak) {
        if (streak >= 365) return StreakLevel.PLATINUM;
        if (streak >= 100) return StreakLevel.GOLD;
        if (streak >= 30) return StreakLevel.SILVER;
        if (streak >= 7) return StreakLevel.BRONZE;
        return StreakLevel.NONE;
    }

    private boolean isUserCheckedInToday(Visitor user, Addiction addiction) {
        return checkInRepository.findByUserAndAddictionAndLastCheckinDate(user, addiction, LocalDate.now()).isPresent();
    }

    private CheckIn getOrCreateCheckIn(Visitor user, Addiction addiction) {
        return checkInRepository.findByUserAndAddiction(user, addiction)
                .orElseGet(() -> {
                    CheckIn newCheckIn = new CheckIn();
                    newCheckIn.setUser(user);
                    newCheckIn.setAddiction(addiction);
                    newCheckIn.setStartDate(LocalDate.now());
                    return newCheckIn;
                });
    }

    private void updateCheckInStatus(CheckIn checkIn, boolean isClean) {
        checkIn.setCurrentStreak(Objects.requireNonNullElse(checkIn.getCurrentStreak(), 0));
        checkIn.setLongestStreak(Objects.requireNonNullElse(checkIn.getLongestStreak(), 0));

        if (!isClean) {
            checkIn.setCurrentStreak(0);
            checkIn.setLevel(StreakLevel.NONE);
        } else {
            checkIn.setCurrentStreak(checkIn.getCurrentStreak() + 1);
            if (checkIn.getCurrentStreak() > checkIn.getLongestStreak()) {
                checkIn.setLongestStreak(checkIn.getCurrentStreak());
            }
            checkIn.setLevel(getLevel(checkIn.getCurrentStreak()));
        }
        checkIn.setLastCheckinDate(LocalDate.now());
    }
}
