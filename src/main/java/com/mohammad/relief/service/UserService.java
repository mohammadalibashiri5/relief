package com.mohammad.relief.service;


import com.mohammad.relief.data.dto.response.ModifiedUserDto;
import com.mohammad.relief.data.dto.request.UserRequestDto;
import com.mohammad.relief.data.dto.response.UserResponseDto;
import com.mohammad.relief.data.entity.Visitor;
import com.mohammad.relief.data.entity.enums.Roles;
import com.mohammad.relief.exception.ReliefApplicationException;
import com.mohammad.relief.mapper.UserMapper;
import com.mohammad.relief.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserResponseDto registerUser(UserRequestDto userRequestDto) throws ReliefApplicationException {
        if (userRequestDto == null) {
            throw new ReliefApplicationException("UserRequestDto is null");
        }
        if (!isDateValid(userRequestDto.dateOfBirth())) {
            throw new ReliefApplicationException("You must be at least 13 years old to register.");
        }

        if (!userRepository.existsByUsername(userRequestDto.username()) && !userRepository.existsByEmail(userRequestDto.email())) {
            String hashedPassed = passwordEncoder.encode(userRequestDto.password());
            Visitor user = userMapper.toEntity(userRequestDto);
            user.setRole(String.valueOf(Roles.ROLE_VISITOR));
            user.setPassword(hashedPassed);
            Visitor savedUser = userRepository.save(user);
            return userMapper.toResponseDto(savedUser);
        } else throw new ReliefApplicationException("Visitor already exists");
    }

    public UserResponseDto updateUser(ModifiedUserDto userResponseDto, String email) throws ReliefApplicationException {
        Optional<Visitor> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new ReliefApplicationException("Visitor not found");
        }
        Visitor foundUser = user.get();
        boolean isUpdated = false;

        if (userResponseDto.name() != null && !foundUser.getName().equals(userResponseDto.name())) {
            foundUser.setName(userResponseDto.name());
            isUpdated = true;
        }
        if (userResponseDto.familyName() != null && !foundUser.getFamilyName().equals(userResponseDto.familyName())) {
            foundUser.setFamilyName(userResponseDto.familyName());
            isUpdated = true;
        }
        if (userResponseDto.password() != null && !foundUser.getPassword().equals(userResponseDto.password())) {
            PasswordEncoder passwordEncoderUpdate = PasswordEncoderFactories.createDelegatingPasswordEncoder();
            foundUser.setPassword(passwordEncoderUpdate.encode(userResponseDto.password()));
            isUpdated = true;
        }
        if (userResponseDto.dateOfBirth() != null && !foundUser.getDateOfBirth().equals(userResponseDto.dateOfBirth())) {
            foundUser.setDateOfBirth(userResponseDto.dateOfBirth());
            isUpdated = true;
        }
        if (isUpdated) {
            userRepository.save(foundUser);
        }


        return userMapper.toResponseDto(foundUser);
    }

    public UserResponseDto getUserDetails(String email) throws ReliefApplicationException {
        Visitor user = findByEmail(email);
        return userMapper.toResponseDto(user);
    }

    public void deleteUser(String name) {
        Optional<Visitor> user = userRepository.findByEmail(name);
        user.ifPresent(userRepository::delete);
    }

    public Visitor findByEmail(String email) throws ReliefApplicationException {
        Optional<Visitor> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            return user.get();
        }else throw new ReliefApplicationException("No such a user");
    }

    private boolean isDateValid(LocalDate dateOfBirth) {
        LocalDate today = LocalDate.now();
        LocalDate thirteenYearsAgo = today.minusYears(13);
        return !dateOfBirth.isAfter(thirteenYearsAgo);
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

}
