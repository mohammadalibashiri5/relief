package com.mohammad.relief.controller;

import com.mohammad.relief.data.entity.Streak;
import com.mohammad.relief.data.entity.enums.StreakLevel;
import com.mohammad.relief.exception.ReliefApplicationException;
import com.mohammad.relief.service.StreakService;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/checkin")
@PreAuthorize("hasAuthority('USER')")
public class CheckInController {

    private final StreakService streakService;

    public CheckInController(StreakService streakService) {
        this.streakService = streakService;
    }

    @PostMapping("/checkin")
    public ResponseEntity<?> checkIn(@RequestParam UUID userId, @RequestParam Integer addictionId, @RequestParam boolean isClean) throws ReliefApplicationException {
        Streak streak = streakService.getStreakByUserIdAndAddiction(userId, addictionId);

        if (!isClean) {
            streak.setCurrentStreak(0);
            streak.setLevel(StreakLevel.NONE);
        } else {
            streak.setCurrentStreak(streak.getCurrentStreak() + 1);

            // Update longest streak
            if (streak.getCurrentStreak() > streak.getLongestStreak()) {
                streak.setLongestStreak(streak.getCurrentStreak());
            }

            // Assign tier
            StreakLevel level = getLevel(streak.getCurrentStreak());
            streak.setLevel(level);
        }

        streak.setLastCheckinDate(LocalDate.now());
        streakService.setStreak(streak);

        return ResponseEntity.ok("Check-in recorded. Your current level: " + streak.getLevel());
    }

    // Function to determine level
    private StreakLevel getLevel(int streak) {
        if (streak >= 365) return StreakLevel.PLATINUM;
        if (streak >= 100) return StreakLevel.GOLD;
        if (streak >= 30) return StreakLevel.SILVER;
        if (streak >= 7) return StreakLevel.BRONZE;
        return StreakLevel.NONE;
    }

    @Scheduled(cron = "0 0 0 * * ?") // Runs at midnight
    public void checkMissedCheckIns() {
        List<Streak> streaks = streakService.getAllStreaks();

        for (Streak streak : streaks) {
            if (!streak.getLastCheckinDate().isEqual(LocalDate.now().minusDays(1))) {
                streak.setCurrentStreak(0); // Reset streak
                streakService.setStreak(streak);
            }
        }
    }

    // Perform today's check-in
//    @PostMapping
//    public ResponseEntity<CheckInResponseDto> performCheckIn(Principal principal) throws ReliefApplicationException {
//
//        String username = principal.getName();  // Extract user's email from the token
//        CheckIn checkIn = checkInService.performCheckIn(username);
//
//        CheckInResponseDto responseDto = checkInMapper.toDto(checkIn);
//
//        return ResponseEntity.ok(responseDto);
//    }
//
//    // Get current streak
//    @GetMapping("/streak")
//    public ResponseEntity<Integer> getUserStreak(Principal principal) throws ReliefApplicationException {
//        String email = principal.getName();
//        User user = userService.findByUsername(email);
//        return ResponseEntity.ok(user.getGlobalStreak());
//    }
//
//    // Retrieve all check-ins (history)
//    @GetMapping("/history")
//    public ResponseEntity<List<CheckInResponseDto>> getCheckInHistory(Principal principal) {
//        String username = principal.getName();
//        List<CheckIn> checkIns = checkInService.getUserCheckInHistory(username);
//
//        List<CheckInResponseDto> checkInDtos = checkIns.stream()
//                .map(checkIn -> new CheckInResponseDto(
//                        checkIn.getDate(),
//                        checkIn.getStatus(),
//                        checkIn.getUser().getGlobalStreak()
//                ))
//                .toList();
//
//        return ResponseEntity.ok(checkInDtos);
//    }
}