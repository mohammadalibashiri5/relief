package com.mohammad.relief.controller;

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
            @RequestParam String addictionName) {
        TriggerResponseDTO triggerResponseDTO = triggerService.addTrigger(triggerRequestDTO, addictionName);

        return new ResponseEntity<>(triggerResponseDTO, HttpStatus.CREATED);
    }

    @PutMapping("/update/{triggerId}")
    public ResponseEntity<TriggerResponseDTO> updateTrigger(
            @RequestBody @Valid TriggerRequestDTO triggerRequestDTO,
            @PathVariable Long triggerId) throws ReliefApplicationException {

        TriggerResponseDTO triggerResponseDTO = triggerService.updateTrigger(triggerRequestDTO, triggerId);
        return new ResponseEntity<>(triggerResponseDTO, HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public List<TriggerResponseDTO> getAllTrigger(Principal principal) throws ReliefApplicationException {
        String username = principal.getName();
        return triggerService.findAll(username);
    }
//    @DeleteMapping("/delete")
//    public void deleteTrigger(@RequestParam String triggerName, Principal principal) throws ReliefApplicationException {
//        String username = principal.getName();
//        triggerService.deleteTrigger(triggerName, username);
//    }
}
