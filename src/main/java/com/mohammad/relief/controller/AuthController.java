package com.mohammad.relief.controller;

import com.mohammad.relief.auth.AuthRequest;
import com.mohammad.relief.auth.AuthResponse;
import com.mohammad.relief.data.entity.CheckIn;
import com.mohammad.relief.service.CheckInService;
import com.mohammad.relief.util.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final CheckInService checkInService;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, CheckInService
                           checkInService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.checkInService = checkInService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );

            // Retrieve user details, including roles
            String token = jwtUtil.generateToken(request.getEmail());
            return ResponseEntity.ok(new AuthResponse(token));

        } catch (AuthenticationException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid email or password.");
        }
    }
}
