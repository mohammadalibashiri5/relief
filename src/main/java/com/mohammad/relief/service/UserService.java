package com.mohammad.relief.service;

import com.mohammad.relief.data.dto.AddictionRequestDto;
import com.mohammad.relief.data.dto.UserRequestDto;
import com.mohammad.relief.data.dto.UserResponseDto;
import com.mohammad.relief.data.entity.Addiction;
import com.mohammad.relief.data.entity.User;
import com.mohammad.relief.mapper.UserMapper;
import com.mohammad.relief.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponseDto registerUser(@Valid @RequestBody UserRequestDto userRequestDto) {
        String hashedPassed = passwordEncoder.encode(userRequestDto.password());
        User user = userMapper.toEntity(userRequestDto);
        user.setRole("ROLE_USER");
        user.setPassword(hashedPassed);
        User savedUser = userRepository.save(user);
        return userMapper.toResponseDto(savedUser);
    }

    public void assignAddictionToUser(AddictionRequestDto dto) {
        User user = userRepository.findById(dto.userId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Addiction addiction = new Addiction(
                dto.name(),
                dto.description(),
                dto.severityLevel(),
                dto.yearOfAddiction()
        );

        user.addAddiction(addiction); // Link addiction to user
        userRepository.save(user);   // Save changes
    }

    public Optional<UserResponseDto> findUserById(UUID id) {
        return userRepository.findById(id)
                .map(userMapper::toResponseDto);
    }

    public Optional<UserResponseDto> findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(userMapper::toResponseDto);
    }

    public UserResponseDto updateUser(UUID id, UserRequestDto userRequestDto) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // Update fields
        existingUser.setUsername(userRequestDto.username());
        existingUser.setEmail(userRequestDto.email());
        if (userRequestDto.password() != null && !userRequestDto.password().isBlank()) {
            String hashedPassword = passwordEncoder.encode(userRequestDto.password());
            existingUser.setPassword(hashedPassword); // Set hashed password
        }

        User updatedUser = userRepository.save(existingUser);
        return userMapper.toResponseDto(updatedUser);
    }
}
