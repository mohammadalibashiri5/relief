package com.mohammad.relief.controller.addiction;

import com.mohammad.relief.data.dto.request.UserAddictionRequestDto;
import com.mohammad.relief.data.dto.response.UserAddictionResponseDto;
import com.mohammad.relief.data.entity.UserAddiction;
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
    public ResponseEntity<UserAddictionResponseDto> createAddiction(@Valid @RequestBody UserAddictionRequestDto requestDto, @RequestParam Long addictionId, Principal principal) throws ReliefApplicationException {
        String email = principal.getName();
        return ResponseEntity.ok(userAddictionService.createUserAddiction(requestDto,addictionId, email));
    }
    @GetMapping
    @PreAuthorize("hasRole('VISITOR')")
    public ResponseEntity<List<UserAddictionResponseDto>> getAddictionByAddictionId(Principal principal) throws ReliefApplicationException {
        String email = principal.getName();
        return ResponseEntity.ok(userAddictionService.getAllUserAddiction(email));

    }
}
