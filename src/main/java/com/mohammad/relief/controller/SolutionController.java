package com.mohammad.relief.controller;

import com.mohammad.relief.data.dto.request.SolutionRequestDto;
import com.mohammad.relief.data.dto.response.SolutionResponseDto;
import com.mohammad.relief.exception.ReliefApplicationException;
import com.mohammad.relief.service.SolutionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
public class SolutionController {
    private final SolutionService solutionService;

    @PostMapping("solution")
    public ResponseEntity<SolutionResponseDto> addSolution(@RequestBody @Valid SolutionRequestDto requestDto, Principal principal, @RequestParam String triggerName) throws ReliefApplicationException {
        String username = principal.getName();
        SolutionResponseDto addedSolution = solutionService.addSolution(requestDto, username, triggerName);
        return new ResponseEntity<>(addedSolution, HttpStatus.CREATED);
    }
    @GetMapping("solutions")
    public ResponseEntity<List<SolutionResponseDto>> getAllSolutions() {
        List<SolutionResponseDto> solutions = solutionService.getAllSolutions();
        return new ResponseEntity<>(solutions, HttpStatus.OK);
    }
    @GetMapping("solution/{solutionName}")
    public ResponseEntity<SolutionResponseDto> getSolution(@PathVariable String solutionName) throws ReliefApplicationException {
        SolutionResponseDto foundSolution = solutionService.getSolutionByName(solutionName);
        return new ResponseEntity<>(foundSolution, HttpStatus.OK);
    }
    @DeleteMapping("solution/{solutionId}")
    public ResponseEntity<Void> deleteSolution(@PathVariable Long solutionId) throws ReliefApplicationException {
        solutionService.deleteSolutionById(solutionId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
