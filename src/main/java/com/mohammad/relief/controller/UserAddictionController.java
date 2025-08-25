package com.mohammad.relief.controller;

import com.mohammad.relief.data.dto.request.AddictionRequestDto;
import com.mohammad.relief.data.dto.response.AddictionResponseDto;
import com.mohammad.relief.exception.ReliefApplicationException;
import com.mohammad.relief.service.AddictionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

/**
 * This controller allows the user to create their own addictions.
 *
 * @deprecated Since this is admin who creates the addictions, and the user selects them.
 * {@link com.mohammad.relief.controller.addiction.AdminAddictionController}
 */

@Deprecated(forRemoval = true)
@RestController
@RequestMapping("/api/v1/user/")
@RequiredArgsConstructor
public class UserAddictionController {

    private final AddictionService addictionService;

    @Operation(summary = "Get all addictions for the current user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful retrieval"),
            @ApiResponse(responseCode = "403", description = "forbidden")
    })
    @GetMapping
    @PreAuthorize("hasAuthority('USER')")
    public List<AddictionResponseDto> getUserAddictions(Principal principal) throws ReliefApplicationException {
        String username = principal.getName();
        return addictionService.getAllUserAddictions(username);
    }

    @GetMapping("/addiction")
    @PreAuthorize("hasAuthority('USER')")
    public AddictionResponseDto getUserAddictionByName(@RequestParam String addictionName, Principal principal) throws ReliefApplicationException {
        String username = principal.getName();
        return addictionService.getAddictionByName(username, addictionName);

    }
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public AddictionResponseDto getUserAddictionById(@PathVariable Long id, Principal principal) throws ReliefApplicationException {
        String username = principal.getName();
        return addictionService.getAddictionDtoByIdAndUser(id, username);
    }

    @PostMapping("/addictions")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public ResponseEntity<AddictionResponseDto> assignAddictionToUser(
            @RequestBody AddictionRequestDto addictionDto,
            Principal principal) throws ReliefApplicationException {

        String username = principal.getName();
        AddictionResponseDto addiction = addictionService.assignAddictionToUser(addictionDto, username);

        return ResponseEntity.status(HttpStatus.CREATED).body(addiction);
    }


    @Operation(summary = "modify the addiction by id for the current user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful retrieval"),
            @ApiResponse(responseCode = "403", description = "forbidden")
    })
    @PutMapping("/{addictionId}")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public ResponseEntity<String> updateAddiction(
            @PathVariable Long addictionId,
            @RequestBody AddictionRequestDto addictionRequestDto,
            Principal principal) {
        try {
            String username = principal.getName();
            AddictionResponseDto updatedAddiction = addictionService.updateAddictionOfUser(
                    addictionRequestDto, addictionId, username);
            return ResponseEntity.ok(updatedAddiction + " was updated");
        } catch (ReliefApplicationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("The addiction could not be updated");
        }
    }

    @DeleteMapping("/addictions/{id}")
    public ResponseEntity<Void> deleteAddiction(@PathVariable Long id,
                                                Principal principal) throws ReliefApplicationException {
        String username = principal.getName();
        addictionService.deleteAddiction(username, id);
        return ResponseEntity.noContent().build();
    }
}

