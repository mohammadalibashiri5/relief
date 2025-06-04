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

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/solution")
@RequiredArgsConstructor
public class SolutionController {
    private final SolutionService solutionService;

    @PostMapping("/add")
    public ResponseEntity<SolutionResponseDto> addSolution(@RequestBody @Valid SolutionRequestDto requestDto, Principal principal, @RequestParam String triggerName) throws ReliefApplicationException {
        String username = principal.getName();
        SolutionResponseDto addedSolution = solutionService.addSolution(requestDto, username, triggerName);
        return new ResponseEntity<>(addedSolution, HttpStatus.CREATED);
    }
    @GetMapping("/getAll")
    public ResponseEntity<List<SolutionResponseDto>> getAllSolutions() {
        List<SolutionResponseDto> solutions = solutionService.getAllSolutions();
        return new ResponseEntity<>(solutions, HttpStatus.OK);
    }
    @GetMapping("/get/{solutionName}")
    @SneakyThrows(ReliefApplicationException.class)
    public ResponseEntity<SolutionResponseDto> getSolution(@PathVariable String solutionName) {
        SolutionResponseDto foundSolution = solutionService.getSolutionByName(solutionName);
        return new ResponseEntity<>(foundSolution, HttpStatus.OK);
    }
    @DeleteMapping("/{solutionId}")
    @SneakyThrows(ReliefApplicationException.class)
    public ResponseEntity<Void> deleteSolution(@PathVariable Long solutionId) {
        solutionService.deleteSolutionById(solutionId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
