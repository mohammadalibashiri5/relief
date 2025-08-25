package com.mohammad.relief.controller.addiction;

import com.mohammad.relief.data.dto.request.UserAddictionRequestDto;
import com.mohammad.relief.data.dto.response.AdminAddictionResponse;
import com.mohammad.relief.data.dto.response.UserAddictionResponseDto;
import com.mohammad.relief.exception.ReliefApplicationException;
import com.mohammad.relief.service.UserAddictionService;
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
public class AddictionController {
    private final UserAddictionService userAddictionService;

    @PostMapping
    @PreAuthorize("hasRole('VISITOR')")
    public ResponseEntity<UserAddictionResponseDto> createAddiction(@Valid @RequestBody UserAddictionRequestDto requestDto, @RequestParam String addictionName, Principal principal) throws ReliefApplicationException {
        String email = principal.getName();
        return ResponseEntity.ok(userAddictionService.createUserAddiction(requestDto,addictionName, email));
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
}
