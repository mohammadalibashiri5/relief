package com.mohammad.relief.controller;

import com.mohammad.relief.data.dto.request.UserRequestDto;
import com.mohammad.relief.data.dto.response.UserResponseDto;
import com.mohammad.relief.exception.ReliefApplicationException;
import com.mohammad.relief.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admins")
public class AdminController {
    private final UserService userService;

    @PostMapping("/register")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<UserResponseDto> registerUser(@Valid @RequestBody UserRequestDto userRequestDto) throws ReliefApplicationException {
        UserResponseDto userResponse = userService.registerAdmin(userRequestDto);
        return ResponseEntity.ok(userResponse);
    }
    @GetMapping("/getUser")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<UserResponseDto> getUserDetails(Principal principal) throws ReliefApplicationException {
        String email = principal.getName();
        UserResponseDto admin = userService.getAdminDetails(email);
        return ResponseEntity.ok(admin);
    }



}
