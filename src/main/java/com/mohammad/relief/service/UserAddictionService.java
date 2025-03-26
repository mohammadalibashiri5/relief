package com.mohammad.relief.service;

import com.mohammad.relief.data.dto.request.AddictionRequestDto;
import com.mohammad.relief.data.dto.response.AddictionResponseDto;
import com.mohammad.relief.data.entity.Addiction;
import com.mohammad.relief.data.entity.User;
import com.mohammad.relief.exception.ReliefApplicationException;
import com.mohammad.relief.mapper.AddictionMapper;
import com.mohammad.relief.repository.AddictionRepository;
import com.mohammad.relief.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserAddictionService {
    private final AddictionRepository addictionRepository;
    private final AddictionMapper addictionMapper;
    private final UserService userService;
    private final UserRepository userRepository;


    public AddictionResponseDto assignAddictionToUser(AddictionRequestDto addictionDto, String username) throws ReliefApplicationException {
        // Retrieve user by username
        User user = userService.findByUsername(username);

        // Check if addiction already exists for the user
        boolean addictionExists = user.getAddictions().stream()
                .anyMatch(a -> a.getName().equalsIgnoreCase(addictionDto.name()));

        if (addictionExists) {
            throw new ReliefApplicationException("Addiction already assigned to user");
        }

        // Map DTO to entity
        Addiction addiction = addictionMapper.toEntity(addictionDto);
        addiction.setUser(user);  // Link addiction to user
        addiction.setYearOfAddiction(addictionDto.yearOfAddiction());

        // Save the addiction
        addiction = addictionRepository.save(addiction);

        // Convert to DTO and return
        return new AddictionResponseDto(
                addiction.getName(),
                addiction.getDescription(),
                addiction.getSeverityLevel(),
                addiction.getYearOfAddiction()
        );
    }



    public AddictionResponseDto updateAddictionOfUser(AddictionRequestDto addictionRequestDto,
                                                      String addictionName,
                                                      String username
    ) throws ReliefApplicationException {

        User user = userService.findByUsername(username);

        // Find the addiction by name
        Addiction addiction = user.getAddictions().stream()
                .filter(a -> a.getName().equals(addictionName))
                .findFirst()
                .orElseThrow(() -> new ReliefApplicationException("Addiction not found"));

        // Update only non-null fields
        if (addictionRequestDto.description() != null) {
            addiction.setDescription(addictionRequestDto.description());
        }
        if (addictionRequestDto.yearOfAddiction() != null) {
            addiction.setYearOfAddiction(addictionRequestDto.yearOfAddiction());
        }
        if (addictionRequestDto.severityLevel() != null) {
            addiction.setSeverityLevel(addictionRequestDto.severityLevel());
        }

        // Save updated user with modified addiction list
        userRepository.save(user);

        // Return updated addiction details
        return addictionMapper.toDto(addiction);
    }

    @Transactional
    public void deleteAddiction(String username, String addictionName) throws ReliefApplicationException {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new ReliefApplicationException("User not found");
        }
        // Find addiction by name
        Addiction addiction = addictionRepository.findByName(addictionName)
                .orElseThrow(() -> new ReliefApplicationException("Addiction not found"));
        // Delete from the repository
        addictionRepository.delete(addiction);
    }


    public Addiction getAddictionByName(String addictionName) {
        // Save before using it in CheckIn
        return addictionRepository.findByName(addictionName)
                .orElseGet(() -> {
                    Addiction newAddiction = new Addiction();
                    newAddiction.setName(addictionName);
                    return addictionRepository.save(newAddiction); // Save before using it in CheckIn
                });
    }

    public List<AddictionResponseDto> getAllAddictions(String username) throws ReliefApplicationException {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new ReliefApplicationException("User not found");
        }
        else return addictionRepository.findAll().stream().map(addictionMapper::toDto).collect(Collectors.toList());
    }
}
