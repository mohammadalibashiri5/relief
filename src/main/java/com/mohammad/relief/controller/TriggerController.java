package com.mohammad.relief.controller;

import com.mohammad.relief.data.dto.request.TriggerRequestDTO;
import com.mohammad.relief.data.dto.response.TriggerResponseDTO;
import com.mohammad.relief.exception.ReliefApplicationException;
import com.mohammad.relief.service.TriggerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trigger")
@RequiredArgsConstructor
public class TriggerController {
    private final TriggerService triggerService;


    @PostMapping("/add")
    @SneakyThrows(ReliefApplicationException.class)
    public ResponseEntity<TriggerResponseDTO> addTrigger(
            @RequestBody @Valid TriggerRequestDTO triggerRequestDTO,
            @RequestParam Long addictionId) {
        TriggerResponseDTO triggerResponseDTO = triggerService.addTrigger(triggerRequestDTO, addictionId);

        return new ResponseEntity<>(triggerResponseDTO, HttpStatus.CREATED);
    }

    @PutMapping("/update/{triggerId}")
    public ResponseEntity<TriggerResponseDTO> updateTrigger(
            @RequestBody @Valid TriggerRequestDTO triggerRequestDTO,
            @PathVariable Long triggerId) throws ReliefApplicationException {

        TriggerResponseDTO triggerResponseDTO = triggerService.updateTrigger(triggerRequestDTO, triggerId);
        return new ResponseEntity<>(triggerResponseDTO, HttpStatus.OK);
    }

    @GetMapping("/getByAddiction/{addictionId}")
    public List<TriggerResponseDTO> getAllTriggerByAddiction(@PathVariable Long addictionId) {
        return triggerService.findAllByAddictionId(addictionId);
    }
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteTrigger(@RequestParam String triggerName) throws ReliefApplicationException {
        triggerService.deleteTrigger(triggerName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
