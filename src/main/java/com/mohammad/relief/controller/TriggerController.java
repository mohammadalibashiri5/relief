package com.mohammad.relief.controller;

import com.mohammad.relief.data.dto.request.SolutionRequestDto;
import com.mohammad.relief.data.dto.request.TriggerRequestDTO;
import com.mohammad.relief.data.dto.response.TriggerResponseDTO;
import com.mohammad.relief.data.entity.Trigger;
import com.mohammad.relief.exception.ReliefApplicationException;
import com.mohammad.relief.mapper.TriggerMapper;
import com.mohammad.relief.repository.TriggerRepository;
import com.mohammad.relief.service.TriggerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import static org.apache.commons.lang3.StringUtils.EMPTY;

@RestController
@RequestMapping("/api/trigger")
@RequiredArgsConstructor
public class TriggerController {
    private final TriggerService triggerService;
    private final TriggerRepository triggerRepository;
    private final TriggerMapper triggerMapper;


    @PostMapping("/add")
    public ResponseEntity<TriggerResponseDTO> addTrigger(
            @RequestBody @Valid TriggerRequestDTO triggerRequestDTO,
            Principal principal,
            @RequestParam String addictionName) throws ReliefApplicationException {
        String username = principal.getName();
        // Call the service to handle the logic
        TriggerResponseDTO triggerResponseDTO = triggerService.addTrigger(triggerRequestDTO, username, addictionName);

        // Return the response with HTTP status CREATED (201)
        return new ResponseEntity<>(triggerResponseDTO, HttpStatus.CREATED);
    }
    @PutMapping("/update/{triggerName}")
    public ResponseEntity<TriggerResponseDTO> updateTrigger(
            @PathVariable String triggerName,
            @RequestBody @Valid TriggerRequestDTO updatedTriggerDTO) throws ReliefApplicationException {

        Optional<Trigger> optionalTrigger = triggerService.getByName(triggerName);

        if (optionalTrigger.isPresent()) {
            Trigger trigger = optionalTrigger.get();

            // Update only the fields that were provided in the request
            trigger.setDescription(updatedTriggerDTO.name());
            trigger.setDescription(updatedTriggerDTO.description());

            Trigger updatedTrigger = triggerRepository.save(trigger);
            return ResponseEntity.ok(triggerMapper.toDto(updatedTrigger));
        }

        throw new ReliefApplicationException("Trigger not found: " + triggerName);
    }
//    @PutMapping("/update")
//    public ResponseEntity<TriggerResponseDTO> updateTrigger(
//            @RequestBody @Valid TriggerRequestDTO triggerRequestDTO,
//            Principal principal,
//            @RequestParam String triggerName) throws ReliefApplicationException {
//        String username = principal.getName();
//
//        TriggerResponseDTO triggerResponseDTO = triggerService.updateTrigger(triggerRequestDTO, username,triggerName);
//        return new ResponseEntity<>(triggerResponseDTO, HttpStatus.OK);
//    }
//    @GetMapping("/getAll")
//    public List<TriggerResponseDTO> getAllTrigger(Principal principal) throws ReliefApplicationException {
//        String username = principal.getName();
//        return triggerService.findAll(username);
//    }
//    @DeleteMapping("/delete")
//    public void deleteTrigger(@RequestParam String triggerName, Principal principal) throws ReliefApplicationException {
//        String username = principal.getName();
//        triggerService.deleteTrigger(triggerName, username);
//    }
}
