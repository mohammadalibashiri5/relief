package com.mohammad.relief.controller;

import com.mohammad.relief.data.dto.request.AddictionRequestDto;
import com.mohammad.relief.data.dto.response.AddictionResponseDto;
import com.mohammad.relief.exception.ReliefApplicationException;
import com.mohammad.relief.service.UserAddictionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/user/")
@RequiredArgsConstructor
public class UserAddictionController {

    private final UserAddictionService userAddictionService;

    @GetMapping("/addictions")
    @PreAuthorize("hasAuthority('USER')")
    public List<AddictionResponseDto> getUserAddiction(Principal principal) throws ReliefApplicationException {
        String username = principal.getName();
        return userAddictionService.getAllUserAddictions(username);

    }
    @GetMapping("/addiction")
    @PreAuthorize("hasAuthority('USER')")
    public AddictionResponseDto getUserAddictionByName(@RequestParam String addictionName ,Principal principal) throws ReliefApplicationException {
        String username = principal.getName();
        return userAddictionService.getAddictionByName(username, addictionName);

    }

    @GetMapping("/addiction/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public AddictionResponseDto getUserAddictionById(@PathVariable Long id, Principal principal) throws ReliefApplicationException {
        String username = principal.getName();
        return userAddictionService.getAddictionDtoByIdAndUser(id, username);
    }

    @PostMapping("/addictions")
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
            String username = principal.getName();
            AddictionResponseDto updatedAddiction = userAddictionService.updateAddictionOfUser(
                    addictionRequestDto, addictionId, username);
            return ResponseEntity.ok(updatedAddiction);
        } catch (ReliefApplicationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("The addiction could not be updated");
        }
    }

    @DeleteMapping("/addictions/{id}")
    public ResponseEntity<Void> deleteAddiction(@PathVariable Long id,
                                                Principal principal) throws ReliefApplicationException {
        String username = principal.getName();
        userAddictionService.deleteAddiction(username, id);
        return ResponseEntity.noContent().build();
    }


}
