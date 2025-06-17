package com.mohammad.relief.controller;

import com.mohammad.relief.data.dto.request.AddictionRequestDto;
import com.mohammad.relief.data.dto.response.AddictionResponseDto;
import com.mohammad.relief.exception.ReliefApplicationException;
import com.mohammad.relief.service.UserAddictionService;
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

@RestController
@RequestMapping("/api/v1/user/addictions")
@RequiredArgsConstructor
public class UserAddictionController {

    private final UserAddictionService userAddictionService;

    @Operation(summary = "Get all addictions for the current user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful retrieval"),
            @ApiResponse(responseCode = "403", description = "forbidden")
    })
    @GetMapping
    @PreAuthorize("hasAuthority('USER')")
    public List<AddictionResponseDto> getUserAddictions(Principal principal) throws ReliefApplicationException {
        String username = principal.getName();
        return userAddictionService.getAllUserAddictions(username);
    }


    @Operation(summary = "Get addiction by the id for the current user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful retrieval"),
            @ApiResponse(responseCode = "403", description = "forbidden")
    })
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public AddictionResponseDto getUserAddictionById(@PathVariable Long id, Principal principal) throws ReliefApplicationException {
        String username = principal.getName();
        return userAddictionService.getAddictionDtoByIdAndUser(id, username);
    }

    @Operation(summary = "Get addiction by name for the current user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful retrieval"),
            @ApiResponse(responseCode = "403", description = "forbidden")
    })
    @GetMapping("/search")
    @PreAuthorize("hasAuthority('USER')")
    public AddictionResponseDto getUserAddictionByName(@RequestParam String name, Principal principal) throws ReliefApplicationException {
        String username = principal.getName();
        return userAddictionService.getAddictionByName(username, name);
    }


    @Operation(summary = "add the addiction for the authentified user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully add"),
            @ApiResponse(responseCode = "403", description = "forbidden")
    })
    @PostMapping
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public ResponseEntity<AddictionResponseDto> assignAddictionToUser(
            @RequestBody AddictionRequestDto addictionDto,
            Principal principal) throws ReliefApplicationException {

        String username = principal.getName();
        AddictionResponseDto addiction = userAddictionService.assignAddictionToUser(addictionDto, username);

        return ResponseEntity.status(HttpStatus.CREATED).body(addiction);
    }


    @Operation(summary = "modify the addiction by id for the current user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful retrieval"),
            @ApiResponse(responseCode = "403", description = "forbidden")
    })
    @PutMapping("/{addictionId}")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public ResponseEntity<?> updateAddiction(
            @PathVariable Long addictionId,
            @RequestBody AddictionRequestDto addictionRequestDto,
            Principal principal) {
        try {
            String username = principal.getName();
            AddictionResponseDto updatedAddiction = userAddictionService.updateAddictionOfUser(
                    addictionRequestDto, addictionId, username);
            return ResponseEntity.ok(updatedAddiction);
        } catch (ReliefApplicationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("The addiction could not be updated");
        }
    }



    @Operation(summary = "delete the addiction by id for the current user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No Content"),
            @ApiResponse(responseCode = "403", description = "forbidden")
    })
    @DeleteMapping("/{name}")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public ResponseEntity<Void> deleteAddiction(@PathVariable String name, Principal principal) throws ReliefApplicationException {
        String username = principal.getName();
        userAddictionService.deleteAddiction(username, name);
        return ResponseEntity.noContent().build();
    }
}

