package com.mohammad.relief.features.userSide.userAddiction.api;

import com.mohammad.relief.features.adminSide.aminAddiction.api.AdminAddictionResponse;
import com.mohammad.relief.common.exception.ReliefApplicationException;
import com.mohammad.relief.features.userSide.userAddiction.domain.UserAddictionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/user-addictions")
@RequiredArgsConstructor
public class UserAddictionController {
    private final UserAddictionService userAddictionService;

    @PostMapping
    @PreAuthorize("hasRole('VISITOR')")
    public ResponseEntity<UserAddictionResponseDto> createAddiction(@Valid @RequestBody UserAddictionRequestDto requestDto, @RequestParam String addictionName, Principal principal) throws ReliefApplicationException {
        String email = principal.getName();
        return ResponseEntity.ok(userAddictionService.createUserAddiction(requestDto,addictionName, email));
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('VISITOR')")
    public ResponseEntity<UserAddictionResponseDto> updateAddictionById(@PathVariable Long id, @Valid @RequestBody UserAddictionRequestDto requestDto, Principal principal) throws ReliefApplicationException {
        String email = principal.getName();
        return ResponseEntity.ok(userAddictionService.updateAddictionByIdAndUser(id, requestDto, email));
    }
    @PreAuthorize("hasRole('VISITOR')")
    @GetMapping("/byCategoryType")
    public ResponseEntity<List<AdminAddictionResponse>> getAllAddictionByCategoryType(@RequestParam String categoryType) throws ReliefApplicationException {
        return ResponseEntity.ok(userAddictionService.getAllAddictionsByCategoryType(categoryType));
    }
    @GetMapping
    @PreAuthorize("hasRole('VISITOR')")
    public ResponseEntity<List<UserAddictionResponseDto>> getAllAddictions(Principal principal) throws ReliefApplicationException {
        String email = principal.getName();
        return ResponseEntity.ok(userAddictionService.getAllUserAddiction(email));

    }
    @PreAuthorize("hasRole('VISITOR')")
    @DeleteMapping( "/{addictionId}")
    public ResponseEntity<Void> deleteAddictionById( @PathVariable Long addictionId, Principal principal) throws ReliefApplicationException {
        String email = principal.getName();
        userAddictionService.deleteAddictionByIdAndUser(addictionId, email);
        return ResponseEntity.noContent().build();

    }
}
