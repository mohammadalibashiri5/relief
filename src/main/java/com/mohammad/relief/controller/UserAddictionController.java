package com.mohammad.relief.controller;

import com.mohammad.relief.data.dto.request.AddictionRequestDto;
import com.mohammad.relief.data.dto.response.AddictionResponseDto;
import com.mohammad.relief.exception.ReliefApplicationException;
import com.mohammad.relief.service.UserAddictionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
public class UserAddictionController {
    private final UserAddictionService userAddictionService;

    public UserAddictionController(UserAddictionService userAddictionService) {
        this.userAddictionService = userAddictionService;
    }

    @PostMapping("/add-addiction")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public ResponseEntity<AddictionResponseDto> assignAddictionToUser(
            @RequestBody AddictionRequestDto addictionDto,
            Principal principal) throws ReliefApplicationException {

        // Extract user email from token
        String username = principal.getName();

        // Call service to assign addiction
        AddictionResponseDto addiction = userAddictionService.assignAddictionToUser(addictionDto, username);

        return ResponseEntity.ok(addiction);
    }


    @PutMapping("/update/{addictionName}")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public ResponseEntity<?> updateAddiction(
            @PathVariable String addictionName,
            @RequestBody AddictionRequestDto addictionRequestDto,
            Principal principal) {
        try {
        // Extract username (email) from token
        String username = principal.getName();

        // Call the addiction service to update the addiction for the user
        AddictionResponseDto updatedAddiction = userAddictionService.updateAddictionOfUser(
                addictionRequestDto, addictionName, username);

        return ResponseEntity.ok(updatedAddiction);
        }
        catch (ReliefApplicationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("The addiction could not be updated");
        }
    }

    @DeleteMapping("/delete/{name}")
    public ResponseEntity<String> deleteAddiction(@PathVariable String name,
                                                  Principal principal) throws ReliefApplicationException {
        String username = principal.getName();
        userAddictionService.deleteAddiction(name);
        return ResponseEntity.ok("Addiction deleted from system.");
    }


}
