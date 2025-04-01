package com.mohammad.relief.controller;

import com.mohammad.relief.data.dto.request.SolutionRequestDto;
import com.mohammad.relief.data.dto.response.SolutionResponseDto;
import com.mohammad.relief.exception.ReliefApplicationException;
import com.mohammad.relief.service.SolutionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/solution")
@RequiredArgsConstructor
public class SolutionController {
    private final SolutionService solutionService;

    @PostMapping("/add")
    public SolutionResponseDto addSolution(@RequestBody @Valid SolutionRequestDto requestDto, Principal principal, @RequestParam String triggerName) throws ReliefApplicationException {
        String username = principal.getName();
        return solutionService.addSolution(requestDto, username, triggerName);
    }
}
