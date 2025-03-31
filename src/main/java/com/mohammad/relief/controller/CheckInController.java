package com.mohammad.relief.controller;

import com.mohammad.relief.data.dto.response.CheckInResponseDto;
import com.mohammad.relief.data.entity.Addiction;
import com.mohammad.relief.data.entity.CheckIn;
import com.mohammad.relief.data.entity.Visitor;
import com.mohammad.relief.exception.ReliefApplicationException;
import com.mohammad.relief.service.CheckInService;
import com.mohammad.relief.service.UserAddictionService;
import com.mohammad.relief.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/checkin")
@PreAuthorize("hasAuthority('USER')")
@RequiredArgsConstructor
public class CheckInController {

    private final CheckInService checkInService;

    @PostMapping("/register")
    public ResponseEntity<CheckInResponseDto> checkIn(Principal principal,
                                                      @RequestParam String addictionName,
                                                      @RequestParam boolean isClean)
            throws ReliefApplicationException {
        String username = principal.getName();

        // Call the service to process the streak
        CheckInResponseDto updatedCheckIn = checkInService.register(username, addictionName, isClean);

        return ResponseEntity.ok(updatedCheckIn);
    }

//    @Scheduled(cron = "0 0 0 * * ?") // Runs at midnight
//    public void checkMissedCheckIns() {
//        List<CheckIn> checkIns = checkInService.getAllStreaks();
//
//        for (CheckIn checkIn : checkIns) {
//            if (!checkIn.getLastCheckinDate().isEqual(LocalDate.now().minusDays(1))) {
//                checkIn.setCurrentStreak(0); // Reset streak
//                checkInService.register(checkIn);
//            }
//        }
//    }
}