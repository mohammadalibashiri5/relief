package com.mohammad.relief.controller;

import com.mohammad.relief.data.dto.request.TriggerRequestDTO;
import com.mohammad.relief.data.dto.response.TriggerResponseDTO;
import com.mohammad.relief.exception.ReliefApplicationException;
import com.mohammad.relief.service.TriggerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.EMPTY;

@RestController
@RequestMapping("/api/trigger")
public class TriggerController {
    private final TriggerService triggerService;
    public TriggerController(TriggerService triggerService) {
        this.triggerService = triggerService;
    }
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
    @PutMapping("/update")
    public ResponseEntity<TriggerResponseDTO> updateTrigger(
            @RequestBody @Valid TriggerRequestDTO triggerRequestDTO,
            Principal principal,
            @RequestParam String triggerName) throws ReliefApplicationException {
        String username = principal.getName();

        TriggerResponseDTO triggerResponseDTO = triggerService.updateTrigger(triggerRequestDTO, username,triggerName);
        return new ResponseEntity<>(triggerResponseDTO, HttpStatus.OK);
    }
    @GetMapping("/getAll")
    public List<TriggerResponseDTO> getAllTrigger(Principal principal) throws ReliefApplicationException {
        String username = principal.getName();
        return triggerService.findAll(username);
    }
    @DeleteMapping("/delete")
    public void deleteTrigger(@RequestParam String triggerName, Principal principal) throws ReliefApplicationException {
        String username = principal.getName();
        triggerService.deleteTrigger(triggerName, username);
    }
}
