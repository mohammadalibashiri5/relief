package com.mohammad.relief.controller;

import com.mohammad.relief.data.dto.request.TriggerRequestDTO;
import com.mohammad.relief.exception.ReliefApplicationException;
import com.mohammad.relief.service.BreakStreakService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/breakAddiction")
public class BreakController {
    private final BreakStreakService breakStreakService;
    public BreakController(BreakStreakService breakStreakService) {
        this.breakStreakService = breakStreakService;
    }

    @PostMapping("/break")
    public ResponseEntity<?> logBreak(
            @RequestBody @Valid TriggerRequestDTO triggerRequestDTO,
            @RequestParam String addictionName,
            Principal principal) throws ReliefApplicationException {

        String username = principal.getName();

        breakStreakService.AddictionBreakStreak(username, addictionName, triggerRequestDTO);

        return ResponseEntity.ok("Addiction break of "+ addictionName + ". Streak reset.");
    }
}
