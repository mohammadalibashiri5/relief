package com.mohammad.relief.service;

import com.mohammad.relief.data.entity.CheckIn;
import com.mohammad.relief.data.entity.User;
import com.mohammad.relief.exception.ReliefApplicationException;
import com.mohammad.relief.repository.CheckInRepository;
import com.mohammad.relief.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
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
                checkIn.setDate(yesterday);
                checkInRepository.save(checkIn);
            }
        });

        userRepository.save(user);

        CheckIn newCheckIn = new CheckIn(user, today, "completed",getStreak(user));
        return checkInRepository.save(newCheckIn);
    }

    private Integer getStreak(User user) {
        List<CheckIn> checkIns = checkInRepository.findTop7ByUserIdOrderByDateDesc(user.getId()); // Get last 7 check-ins
        int streak = 0;
        LocalDate yesterday = LocalDate.now().minusDays(1);

        for (CheckIn checkIn : checkIns) {
            if (checkIn.getDate().equals(yesterday) && checkIn.getStatus().equals("completed")) {
                streak++;
                yesterday = yesterday.minusDays(1);
            } else {
                break;
            }
        }
        return streak + 1;
    }


    public List<CheckIn> getUserCheckInHistory(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        return null;//checkInRepository.findByUserId(user.getId());
    }
}
