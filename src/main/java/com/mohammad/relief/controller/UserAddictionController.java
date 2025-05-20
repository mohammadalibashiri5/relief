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
import java.util.List;

@RestController
@CrossOrigin(value = "https://localhost:4200")
public class UserAddictionController {
    private final UserAddictionService userAddictionService;

    public UserAddictionController(UserAddictionService userAddictionService) {
        this.userAddictionService = userAddictionService;
    }

    @GetMapping("/addictions")
    @PreAuthorize("hasAuthority('USER')")
    public List<AddictionResponseDto> getUserAddiction(Principal principal) throws ReliefApplicationException {
        String username = principal.getName();
        return userAddictionService.getAllAddictions(username);

    }
    @GetMapping("/addiction/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public AddictionResponseDto getUserAddictionById(@PathVariable Long id) throws ReliefApplicationException {
        return userAddictionService.getAddictionById(id);
    }

    @PostMapping("/add-addiction")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public ResponseEntity<AddictionResponseDto> assignAddictionToUser(
            @RequestBody AddictionRequestDto addictionDto,
            Principal principal) throws ReliefApplicationException {

        String username = principal.getName();

        AddictionResponseDto addiction = userAddictionService.assignAddictionToUser(addictionDto, username);

        return ResponseEntity.ok(addiction);
    }


    @PutMapping("/update/{addictionId}")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public ResponseEntity<?> updateAddiction(
            @PathVariable Long addictionId,
            @RequestBody AddictionRequestDto addictionRequestDto,
            Principal principal) {
        try {
        // Extract username (email) from token
        String username = principal.getName();

        // Call the addiction service to update the addiction for the user
        AddictionResponseDto updatedAddiction = userAddictionService.updateAddictionOfUser(
                addictionRequestDto, addictionId, username);

        return ResponseEntity.ok(updatedAddiction);
        }
        catch (ReliefApplicationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("The addiction could not be updated");
        }
    }

    @DeleteMapping("/delete/{name}")
    public ResponseEntity<Void> deleteAddiction(@PathVariable String name,
                                                  Principal principal) throws ReliefApplicationException {
        String username = principal.getName();
        userAddictionService.deleteAddiction(username, name);
        return ResponseEntity.noContent().build();
    }


}
