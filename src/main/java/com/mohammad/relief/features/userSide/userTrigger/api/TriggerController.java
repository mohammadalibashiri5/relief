package com.mohammad.relief.features.userSide.userTrigger.api;

import com.mohammad.relief.common.exception.ReliefApplicationException;
import com.mohammad.relief.features.userSide.userTrigger.domain.TriggerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasRole('VISITOR')")
    @PostMapping(value = {"/add", "/add/{globalTriggerId}"})
    public ResponseEntity<TriggerResponseDto> addTrigger(
            @RequestBody @Valid UserTriggerRequestDto triggerRequestDTO,
            @PathVariable(name = "globalTriggerId", required = false) Long globalTriggerId, // Utilise Long (objet) au lieu de long
            Principal principal) throws ReliefApplicationException {

        // Si l'ID est absent de l'URL, on lui donne une valeur par défaut (0) pour ton service
        long idToUse = (globalTriggerId != null) ? globalTriggerId : 0L;

        String username = principal.getName();
        TriggerResponseDto triggerResponseDTO = triggerService.adoptTrigger(triggerRequestDTO, idToUse, username);

        return new ResponseEntity<>(triggerResponseDTO, HttpStatus.CREATED);
    }
//
//    @Operation(summary = "modify trigger by its id for the connected user")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Successfully updated"),
//            @ApiResponse(responseCode = "403", description = "forbidden")
//    })
//    @PutMapping("/update/{triggerId}")
//    public ResponseEntity<TriggerResponseDto> updateTrigger(
//            @RequestBody @Valid UserTriggerRequestDto triggerRequestDTO,
//            @PathVariable Long triggerId) throws ReliefApplicationException {
//
//        TriggerResponseDto triggerResponseDTO = triggerService.updateTrigger(triggerRequestDTO, triggerId);
//        return new ResponseEntity<>(triggerResponseDTO, HttpStatus.OK);
//    }
//
   @Operation(summary = "Add trigger by addictionId for the current user")
   @ApiResponses(value = {
           @ApiResponse(responseCode = "201", description = "Successfully added"),
           @ApiResponse(responseCode = "403", description = "forbidden")
   })
   @GetMapping("/getByAddiction/{addictionId}")
   public List<TriggerResponseDto> getAllTriggerByAddiction(@PathVariable Long addictionId, Principal principal) throws ReliefApplicationException {
       String emaill = principal.getName();
       return triggerService.getAllTriggers(addictionId, emaill);
   }
//
//    @Operation(summary = "delete the trigger by its id for the connected user")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "204", description = "Successfully deleted"),
//            @ApiResponse(responseCode = "403", description = "forbidden")
//    })
//    @DeleteMapping("/delete/{id}")
//    public ResponseEntity<?> deleteTrigger(@PathVariable Long id) throws ReliefApplicationException {
//        triggerService.deleteTrigger(id);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }
}
