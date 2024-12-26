package com.mohammad.relief.service;

import com.mohammad.relief.data.entity.CheckIn;
import com.mohammad.relief.data.entity.User;
import com.mohammad.relief.repository.AddictionRepository;
import com.mohammad.relief.repository.CheckInRepository;
import com.mohammad.relief.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CheckInService {

    private final CheckInRepository checkInRepository;
    private final UserRepository userRepository;

    public CheckInService(CheckInRepository checkInRepository, UserRepository userRepository) {
        this.checkInRepository = checkInRepository;
        this.userRepository = userRepository;
    }

    public CheckIn performCheckIn(String email) {
        // Find the connected user
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        LocalDate today = LocalDate.now();

        // Check if a check-in for today already exists
        Optional<CheckIn> existingCheckIn = checkInRepository.findByUserIdAndDate(user.getId(), today);

        if (existingCheckIn.isPresent()) {
            // Return the existing check-in to avoid duplicate entries
            return existingCheckIn.get();
        }

        // Mark previous day's check-in as missed, if necessary
        LocalDate yesterday = today.minusDays(1);
        checkInRepository.findByUserIdAndDate(user.getId(), yesterday).ifPresent(checkIn -> {
            if (!checkIn.getStatus().equals("completed")) {
                checkIn.setStatus("missed");
                checkInRepository.save(checkIn);
            }
        });

        // Create a new check-in for today
        CheckIn newCheckIn = new CheckIn();
        newCheckIn.setUser(user);
        newCheckIn.setDate(today);
        newCheckIn.setStatus("completed");

        return checkInRepository.save(newCheckIn);
    }

}
