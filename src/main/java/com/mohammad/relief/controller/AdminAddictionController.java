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

    @PostMapping("/create")
    public ResponseEntity<AdminAddictionResponse> createAddiction(@RequestBody @Valid AdminAddictionRequestDto requestDto, @RequestParam String categoryType, Principal principal) throws ReliefApplicationException {
        String email = principal.getName();
        return ResponseEntity.ok(adminAddictionService.createAddiction(requestDto, categoryType, email));
    }
    @PreAuthorize("permitAll()")
    @GetMapping("")
    public ResponseEntity<List<AdminAddictionResponse>> getAllAddictionByCategoryType(@RequestParam String categoryType) throws ReliefApplicationException {
        return ResponseEntity.ok(adminAddictionService.getAddictionByCategoryName(categoryType));
    }
}
