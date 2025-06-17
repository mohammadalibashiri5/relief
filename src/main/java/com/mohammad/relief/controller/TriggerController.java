package com.mohammad.relief.controller;

import com.mohammad.relief.data.dto.request.TriggerRequestDTO;
import com.mohammad.relief.data.dto.response.TriggerResponseDTO;
import com.mohammad.relief.exception.ReliefApplicationException;
import com.mohammad.relief.service.TriggerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/trigger")
@RequiredArgsConstructor
public class TriggerController {

    private final TriggerService triggerService;

    @Operation(summary = "Add trigger by addictionId for the current user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully added"),
            @ApiResponse(responseCode = "403", description = "forbidden")
    })
    @PostMapping("/add")
    public ResponseEntity<TriggerResponseDTO> addTrigger(
            @RequestBody @Valid TriggerRequestDTO triggerRequestDTO,
            Principal principal,
            @RequestParam Long addictionId) throws ReliefApplicationException {
        String username = principal.getName();
        TriggerResponseDTO triggerResponseDTO = triggerService.addTrigger(triggerRequestDTO, username, addictionId);

        return new ResponseEntity<>(triggerResponseDTO, HttpStatus.CREATED);
    }

    @Operation(summary = "modify trigger by its id for the connected user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated"),
            @ApiResponse(responseCode = "403", description = "forbidden")
    })
    @PutMapping("/update/{triggerId}")
    public ResponseEntity<TriggerResponseDTO> updateTrigger(
            @RequestBody @Valid TriggerRequestDTO triggerRequestDTO,
            @PathVariable Long triggerId) throws ReliefApplicationException {

        TriggerResponseDTO triggerResponseDTO = triggerService.updateTrigger(triggerRequestDTO, triggerId);
        return new ResponseEntity<>(triggerResponseDTO, HttpStatus.OK);
    }

    @Operation(summary = "Add trigger by addictionId for the current user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully added"),
            @ApiResponse(responseCode = "403", description = "forbidden")
    })
    @GetMapping("/getByAddiction/{addictionId}")
    public List<TriggerResponseDTO> getAllTriggerByAddiction(@PathVariable Long addictionId, Principal principal) throws ReliefApplicationException {
        String username = principal.getName();
        return triggerService.getAllTriggersByAddiction(username, addictionId);
    }

    @Operation(summary = "delete the trigger by its id for the connected user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted"),
            @ApiResponse(responseCode = "403", description = "forbidden")
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteTrigger(@PathVariable Long id) throws ReliefApplicationException {
        triggerService.deleteTrigger(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
