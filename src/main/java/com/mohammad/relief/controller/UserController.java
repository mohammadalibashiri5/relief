package com.mohammad.relief.controller;

import com.mohammad.relief.data.dto.AddictionRequestDto;
import com.mohammad.relief.data.dto.UserRequestDto;
import com.mohammad.relief.data.dto.UserResponseDto;
import com.mohammad.relief.service.AddictionService;
import com.mohammad.relief.service.UserService;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> registerUser(@Valid @RequestBody UserRequestDto userRequestDto) {
        UserResponseDto userResponse = userService.registerUser(userRequestDto);
        return ResponseEntity.ok(userResponse);
    }
    @PostMapping("/{userId}/addictions")
    public ResponseEntity<?> assignAddiction(
            @PathVariable UUID userId,
            @RequestBody AddictionRequestDto dto) {

        dto = new AddictionRequestDto(userId, dto.name(), dto.description(), dto.severityLevel(), dto.yearOfAddiction());
        userService.assignAddictionToUser(dto);

        return ResponseEntity.ok("Addiction assigned to user successfully.");
    }

    @GetMapping("/{id}")
    ResponseEntity<UserResponseDto> getUserById(@PathVariable UUID id) {
        return userService.findUserById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("/{email}")
    ResponseEntity<UserResponseDto> getUserByEmail(@PathVariable String email) {
        return userService.findUserByEmail(email).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    ResponseEntity<UserResponseDto> updateUser(@PathVariable UUID id, @Valid @RequestBody UserRequestDto userRequestDto) {
        UserResponseDto updatedUser = userService.updateUser(id, userRequestDto);
        return ResponseEntity.ok(updatedUser);
    }
}

