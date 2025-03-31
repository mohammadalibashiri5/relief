package com.mohammad.relief.service;

import com.mohammad.relief.data.dto.request.SolutionRequestDto;
import com.mohammad.relief.data.dto.request.TriggerRequestDTO;
import com.mohammad.relief.data.dto.response.SolutionResponseDto;
import com.mohammad.relief.data.dto.response.TriggerResponseDTO;
import com.mohammad.relief.data.entity.Solution;
import com.mohammad.relief.data.entity.Trigger;
import com.mohammad.relief.data.entity.User;
import com.mohammad.relief.exception.ReliefApplicationException;
import com.mohammad.relief.mapper.SolutionMapper;
import com.mohammad.relief.repository.SolutionRepository;
import com.mohammad.relief.repository.TriggerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SolutionService {
    private final SolutionRepository solutionRepository;
    private final SolutionMapper solutionMapper;
    private final TriggerService triggerService;
    private final UserService userService;
    private final TriggerRepository triggerRepository;

    public SolutionResponseDto addSolution(SolutionRequestDto solutionRequestDto,String username, String triggerName) throws ReliefApplicationException {
        if (solutionRequestDto == null) {
            throw new ReliefApplicationException("The solution is not assigned");
        }
        User user = userService.findByUsername(username);
        Optional<Trigger> trigger = triggerRepository.findByName(triggerName);
        Solution solution;
        Solution savedSolution = new Solution();
        if (user != null && trigger != null) {
            solution = solutionMapper.toEntity(solutionRequestDto);
            savedSolution = solutionRepository.save(solution);
            return solutionMapper.toDto(savedSolution);
        }
        return solutionMapper.toDto(savedSolution);
    }

    public SolutionResponseDto getSolutionByName(String solutionName) throws ReliefApplicationException {
        SolutionResponseDto responseDto;
        Optional<Solution> solution = solutionRepository.findByName(solutionName);
        if (solution.isEmpty()) {
            throw new ReliefApplicationException("Not Such a content");
        }
        responseDto = solutionMapper.toDto(solution.get());
        return responseDto;
    }
}
