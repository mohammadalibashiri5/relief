package com.mohammad.relief.service;

import com.mohammad.relief.data.dto.response.CheckInResponseDto;
import com.mohammad.relief.data.entity.Addiction;
import com.mohammad.relief.data.entity.CheckIn;
import com.mohammad.relief.data.entity.User;
import com.mohammad.relief.data.entity.enums.StreakLevel;
import com.mohammad.relief.exception.ReliefApplicationException;
import com.mohammad.relief.mapper.CheckInMapper;
import com.mohammad.relief.repository.AddictionRepository;
import com.mohammad.relief.repository.CheckInRepository;
import com.mohammad.relief.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CheckInService {

    private final CheckInRepository checkInRepository;
    private final UserRepository userRepository;
    private final AddictionRepository addictionRepository;
    private final CheckInMapper checkInMapper;

    public CheckInResponseDto register(String username, String addictionName, boolean isClean) throws ReliefApplicationException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ReliefApplicationException("User not found"));

        Addiction addiction = addictionRepository.findByName(addictionName)
                .orElseThrow(() -> new ReliefApplicationException("Addiction not found"));

        // Fetch or create CheckIn
        CheckIn checkIn = checkInRepository.findByUserAndAddiction(user, addiction)
                .orElseGet(() -> {

                    CheckIn newCheckIn = new CheckIn();
                    newCheckIn.setUser(user);
                    newCheckIn.setAddiction(addiction);
                    newCheckIn.setStartDate(LocalDate.now()); // Ensure a start date is set
                    return newCheckIn;
                });

        // Ensure null values are handled
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

        CheckIn savedCheckIn = checkInRepository.save(checkIn);

        return checkInMapper.toDto(savedCheckIn);
    }

    private StreakLevel getLevel(int streak) {
        if (streak >= 365) return StreakLevel.PLATINUM;
        if (streak >= 100) return StreakLevel.GOLD;
        if (streak >= 30) return StreakLevel.SILVER;
        if (streak >= 7) return StreakLevel.BRONZE;
        return StreakLevel.NONE;
    }
}
