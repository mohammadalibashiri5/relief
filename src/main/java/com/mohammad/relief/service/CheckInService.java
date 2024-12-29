package com.mohammad.relief.service;

import com.mohammad.relief.data.entity.CheckIn;
import com.mohammad.relief.data.entity.User;
import com.mohammad.relief.exception.ReliefApplicationException;
import com.mohammad.relief.repository.CheckInRepository;
import com.mohammad.relief.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class CheckInService {

    private final CheckInRepository checkInRepository;
    private final UserRepository userRepository;

    public CheckInService(CheckInRepository checkInRepository, UserRepository userRepository) {
        this.checkInRepository = checkInRepository;
        this.userRepository = userRepository;
    }

    public CheckIn performCheckIn(String username) throws ReliefApplicationException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ReliefApplicationException("User not found"));

        LocalDate today = LocalDate.now();
        Optional<CheckIn> existingCheckIn = checkInRepository.findByUserIdAndDate(user.getId(), today);

        if (existingCheckIn.isPresent()) {
            return existingCheckIn.get();
        }

        LocalDate yesterday = today.minusDays(1);
        checkInRepository.findByUserIdAndDate(user.getId(), yesterday).ifPresent(checkIn -> {
            if (!checkIn.getStatus().equals("completed")) {
                checkIn.setStatus("missed");
                checkInRepository.save(checkIn);
            }
        });

        // Track streaks
        Integer streak = user.getStreak();
        if (streak == null) {
            streak = 0;
        }

        // Check if the user checked in yesterday
        boolean checkedInYesterday = checkInRepository.existsByUserIdAndDate(user.getId(), yesterday);

        if (checkedInYesterday) {
            streak++;  // Increment if they checked in yesterday
        } else {
            streak = 1;  // Reset to 1 if missed a day
        }

        user.setStreak(streak);
        userRepository.save(user);

        CheckIn newCheckIn = new CheckIn(user, today, "completed");
        return checkInRepository.save(newCheckIn);
    }


    public List<CheckIn> getUserCheckInHistory(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        return checkInRepository.findByUserId(user.getId());
    }
}
