package com.mohammad.relief.controller;

import com.mohammad.relief.data.dto.request.AdminAddictionRequestDto;
import com.mohammad.relief.data.dto.response.AdminAddictionResponse;
import com.mohammad.relief.exception.ReliefApplicationException;
import com.mohammad.relief.service.AdminAddictionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/addiction")
@RequiredArgsConstructor
public class AdminAddictionController {
    private final AdminAddictionService adminAddictionService;

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    @PostMapping
    public ResponseEntity<AdminAddictionResponse> createAddiction(@RequestBody @Valid AdminAddictionRequestDto requestDto, @RequestParam String categoryType, Principal principal) throws ReliefApplicationException {
        String email = principal.getName();
        return ResponseEntity.ok(adminAddictionService.createAddiction(requestDto, categoryType, email));
    }
    @PreAuthorize("permitAll()")
    @GetMapping("/byCategoryType")
    public ResponseEntity<List<AdminAddictionResponse>> getAllAddictionByCategoryType(@RequestParam String categoryType) throws ReliefApplicationException {
        return ResponseEntity.ok(adminAddictionService.getAddictionByCategoryName(categoryType));
    }
    @PreAuthorize("permitAll()")
    @GetMapping
    public ResponseEntity<List<AdminAddictionResponse>> getAllAddictionByCategoryType() {
        return ResponseEntity.ok(adminAddictionService.getAllAddictions());
    }
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    @PutMapping
    public ResponseEntity<AdminAddictionResponse> updateAddictionById(@RequestParam Long addictionId, @RequestBody @Valid AdminAddictionRequestDto requestDto, Principal principal) throws ReliefApplicationException {
        String email = principal.getName();
        return ResponseEntity.ok(adminAddictionService.updateAdminAddictionById(addictionId, requestDto, email));
    }
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    @DeleteMapping("/{addictionId}")
    public ResponseEntity<Void> deleteAddictionById(@PathVariable Long addictionId, Principal principal) throws ReliefApplicationException {
        String email = principal.getName();
        adminAddictionService.deleteAdminAddictionById(addictionId, email);
        return ResponseEntity.noContent().build();
    }

}
