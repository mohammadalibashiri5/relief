package com.mohammad.relief.controller;

import com.mohammad.relief.data.dto.response.CheckInResponseDto;
import com.mohammad.relief.data.entity.CheckIn;
import com.mohammad.relief.data.entity.User;
import com.mohammad.relief.exception.ReliefApplicationException;
import com.mohammad.relief.mapper.CheckInMapper;
import com.mohammad.relief.service.CheckInService;
import com.mohammad.relief.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/checkin")
@PreAuthorize("hasAuthority('USER')")
public class CheckInController {

    private final CheckInService checkInService;
    private final UserService userService;
    private final CheckInMapper checkInMapper;

    public CheckInController(CheckInService checkInService, UserService userService, CheckInMapper checkInMapper) {
        this.checkInService = checkInService;
        this.userService = userService;
        this.checkInMapper = checkInMapper;
    }

    // Perform today's check-in
    @PostMapping
    public ResponseEntity<CheckInResponseDto> performCheckIn(Principal principal) throws ReliefApplicationException {

        String username = principal.getName();  // Extract user's email from the token
        CheckIn checkIn = checkInService.performCheckIn(username);

        CheckInResponseDto responseDto = checkInMapper.toDto(checkIn);

        return ResponseEntity.ok(responseDto);
    }

    // Get current streak
    @GetMapping("/streak")
    public ResponseEntity<Integer> getUserStreak(Principal principal) throws ReliefApplicationException {
        String email = principal.getName();
        User user = userService.findByUsername(email);
        return ResponseEntity.ok(user.getGlobalStreak());
    }

    // Retrieve all check-ins (history)
    @GetMapping("/history")
    public ResponseEntity<List<CheckInResponseDto>> getCheckInHistory(Principal principal) {
        String username = principal.getName();
        List<CheckIn> checkIns = checkInService.getUserCheckInHistory(username);

        List<CheckInResponseDto> checkInDtos = checkIns.stream()
                .map(checkIn -> new CheckInResponseDto(
                        checkIn.getDate(),
                        checkIn.getStatus(),
                        checkIn.getUser().getGlobalStreak()
                ))
                .toList();

        return ResponseEntity.ok(checkInDtos);
    }
}