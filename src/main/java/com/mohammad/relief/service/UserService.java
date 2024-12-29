package com.mohammad.relief.service;


import com.mohammad.relief.data.dto.response.ModifiedUserDto;
import com.mohammad.relief.data.dto.request.UserRequestDto;
import com.mohammad.relief.data.dto.response.UserResponseDto;
import com.mohammad.relief.data.entity.User;
import com.mohammad.relief.exception.ReliefApplicationException;
import com.mohammad.relief.mapper.UserMapper;
import com.mohammad.relief.repository.UserRepository;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    public UserResponseDto registerUser(UserRequestDto userRequestDto) throws ReliefApplicationException {
        if (userRequestDto == null) {
            throw new ReliefApplicationException("UserRequestDto is null");
        }

        if (!userRepository.existsByUsername(userRequestDto.username())) {
            String hashedPassed = passwordEncoder.encode(userRequestDto.password());
            User user = userMapper.toEntity(userRequestDto);
            user.setRole("ROLE_USER");
            user.setPassword(hashedPassed);
            User savedUser = userRepository.save(user);
            return userMapper.toResponseDto(savedUser);
        } else throw new ReliefApplicationException("User already exists");
    }

    public UserResponseDto updateUser(ModifiedUserDto userResponseDto, String username) throws ReliefApplicationException {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new ReliefApplicationException("User not found");
        }
        User foundUser = user.get();

        if (userResponseDto.name() != null && !foundUser.getName().equals(userResponseDto.name())) {
            foundUser.setName(userResponseDto.name());
        }
        if (userResponseDto.familyName() != null && !foundUser.getFamilyName().equals(userResponseDto.familyName())) {
            foundUser.setFamilyName(userResponseDto.familyName());
        }
        if (userResponseDto.password() != null && !foundUser.getPassword().equals(userResponseDto.password())) {
            PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
            foundUser.setPassword(passwordEncoder.encode(userResponseDto.password()));
        }
        if (userResponseDto.dateOfBirth() != null && !foundUser.getDateOfBirth().equals(userResponseDto.dateOfBirth())) {
            foundUser.setDateOfBirth(userResponseDto.dateOfBirth());
        }

        userRepository.save(foundUser);
        return userMapper.toResponseDto(foundUser);
    }

    public UserResponseDto getUserDetails(String username) throws ReliefApplicationException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ReliefApplicationException("User not found"));
        return userMapper.toResponseDto(user);
    }

    public void deleteUser(String name) {
        Optional<User> user = userRepository.findByUsername(name);
        user.ifPresent(userRepository::delete);
    }

    public User findByUsername(String username) throws ReliefApplicationException {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            return user.get();
        }else throw new ReliefApplicationException("No such a user");
    }










    /*
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

     */
}
