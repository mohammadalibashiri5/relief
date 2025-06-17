package com.mohammad.relief.controller;

import com.mohammad.relief.data.dto.response.ModifiedUserDto;
import com.mohammad.relief.data.dto.request.UserRequestDto;
import com.mohammad.relief.data.dto.response.UserResponseDto;
import com.mohammad.relief.exception.ReliefApplicationException;
import com.mohammad.relief.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.security.Principal;
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @Operation(summary = "Register a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created"),
            @ApiResponse(responseCode = "403", description = "forbidden")
    })
    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> registerUser(@Valid @RequestBody UserRequestDto userRequestDto) throws ReliefApplicationException {
        UserResponseDto userResponse = userService.registerUser(userRequestDto);
        return ResponseEntity.ok(userResponse);
    }

    @Operation(summary = "update the authentified user's info")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated"),
            @ApiResponse(responseCode = "403", description = "forbidden")
    })
    @PutMapping("/update")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public ResponseEntity<UserResponseDto> updateUser(
            @RequestBody ModifiedUserDto userResponseDto,
            Principal principal) throws ReliefApplicationException {
        String email = principal.getName();
        UserResponseDto updatedUser = userService.updateUser(userResponseDto, email);
        return ResponseEntity.ok(updatedUser);
    }

    @Operation(summary = "Get the user's info")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "403", description = "forbidden")
    })
    @GetMapping("/getUser")
    @PreAuthorize("hasAuthority('ROLE_USER') or hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<UserResponseDto> getUserDetails(Principal principal) throws ReliefApplicationException {
        String email = principal.getName();
        UserResponseDto user = userService.getUserDetails(email);
        return ResponseEntity.ok(user);
    }


    @Operation(summary = "delete the user's account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted"),
            @ApiResponse(responseCode = "403", description = "forbidden")
    })
    @DeleteMapping("/deleteUser")
    ResponseEntity<Void> deleteUser(Principal principal) throws ReliefApplicationException {
        ResponseEntity<Void> re;
        try {
            userService.deleteUser(principal.getName());
            re = new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch(Exception e){
            throw new ReliefApplicationException("Error "+e.getMessage());
        }
        return re;
    }
    @GetMapping("/check-username")
    public ResponseEntity<Boolean> isUsernameAvailable(@RequestParam String username) {
        boolean available = !userService.existsByUsername(username);
        return ResponseEntity.ok(available);
    }

    @GetMapping("/check-email")
    public ResponseEntity<Boolean> isEmailAvailable(@RequestParam String email) {
        boolean available = !userService.existsByEmail(email);
        return ResponseEntity.ok(available);
    }


}

