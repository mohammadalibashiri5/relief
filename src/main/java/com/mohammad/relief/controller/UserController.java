package com.mohammad.relief.controller;

import com.mohammad.relief.data.dto.ModifiedUserDto;
import com.mohammad.relief.data.dto.UserRequestDto;
import com.mohammad.relief.data.dto.UserResponseDto;
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

@RestController
@RequestMapping("/api/users")
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
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public ResponseEntity<UserResponseDto> getUserDetails(Principal principal) throws ReliefApplicationException {
        String username = principal.getName();  // Extract email from JWT
        UserResponseDto user = userService.getUserDetails(username);
        return ResponseEntity.ok(user);
    }

    @ApiResponse(responseCode = "201",description = "La supression à été bien prise en compte")
    @DeleteMapping("/deleteUser")
    ResponseEntity<Void> deleteUser(Principal principal) {
        ResponseEntity<Void> re;
        try {
            userService.deleteUser(principal.getName());
            re = new ResponseEntity<>(HttpStatus.OK);
        }catch(Exception e){
            re = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return re;
    }


}

