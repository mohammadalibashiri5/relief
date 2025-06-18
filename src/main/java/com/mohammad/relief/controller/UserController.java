package com.mohammad.relief.controller;

import com.mohammad.relief.data.dto.response.ModifiedUserDto;
import com.mohammad.relief.data.dto.request.UserRequestDto;
import com.mohammad.relief.data.dto.response.UserResponseDto;
import com.mohammad.relief.exception.ReliefApplicationException;
import com.mohammad.relief.service.UserService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.security.Principal;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> registerUser(@Valid @RequestBody UserRequestDto userRequestDto) throws ReliefApplicationException {
        UserResponseDto userResponse = userService.registerUser(userRequestDto);
        return ResponseEntity.ok(userResponse);
    }


    @PutMapping("/update")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public ResponseEntity<UserResponseDto> updateUser(
            @RequestBody ModifiedUserDto userResponseDto,
            Principal principal) throws ReliefApplicationException {
        String email = principal.getName();
        UserResponseDto updatedUser = userService.updateUser(userResponseDto, email);
        return ResponseEntity.ok(updatedUser);
    }
    @GetMapping("/getUser")
    @PreAuthorize("hasAuthority('ROLE_USER') or hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<UserResponseDto> getUserDetails(Principal principal) throws ReliefApplicationException {
        String email = principal.getName();
        UserResponseDto user = userService.getUserDetails(email);
        return ResponseEntity.ok(user);
    }


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


}

