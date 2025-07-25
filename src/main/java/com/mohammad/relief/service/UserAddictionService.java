package com.mohammad.relief.service;

import com.mohammad.relief.data.dto.request.AddictionRequestDto;
import com.mohammad.relief.data.dto.response.AddictionResponseDto;
import com.mohammad.relief.data.entity.Addiction;
import com.mohammad.relief.data.entity.Visitor;
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
        Visitor user = userRepository.findByEmail(username)
                .orElseThrow(() -> new ReliefApplicationException("No such a user"));

        boolean addictionExists = user.getAddictions().stream()
                .anyMatch(a -> a.getName().equalsIgnoreCase(addictionDto.name()));

        if (addictionExists) {
            throw new ReliefApplicationException("Addiction already assigned to user");
        }

        Addiction addiction = addictionMapper.toEntity(addictionDto);
        addiction.setUser(user);  // Link addiction to user
        addiction.setYearOfAddiction(addictionDto.yearOfAddiction());

        addiction = addictionRepository.save(addiction);

        return addictionMapper.toDto(addiction);
    }



    public AddictionResponseDto updateAddictionOfUser(AddictionRequestDto addictionRequestDto,
                                                      Long addictionId,
                                                      String username
    ) throws ReliefApplicationException {


        Visitor user = userService.findByEmail(username);
        boolean isAddictionUpdate = false;

        // Find the addiction by name
        Addiction addiction = user.getAddictions().stream()
                .filter(a -> a.getId().equals(addictionId))
                .findFirst()
                .orElseThrow(() -> new ReliefApplicationException("Addiction not found"));

        if (addictionRequestDto.name() != null) {
            addiction.setName(addictionRequestDto.name());
            isAddictionUpdate = true;
        }

        if (addictionRequestDto.description() != null) {
            addiction.setDescription(addictionRequestDto.description());
            isAddictionUpdate = true;
        }
        if (addictionRequestDto.yearOfAddiction() != null) {
            addiction.setYearOfAddiction(addictionRequestDto.yearOfAddiction());
            isAddictionUpdate = true;
        }
        if (addictionRequestDto.severityLevel() != null) {
            addiction.setSeverityLevel(addictionRequestDto.severityLevel());
            isAddictionUpdate = true;
        }

        if (isAddictionUpdate) {
            userRepository.save(user);
        }

        // Return updated addiction details
        return addictionMapper.toDto(addiction);
    }

    @Transactional
    public void deleteAddiction(String username, String addictionName) throws ReliefApplicationException {
        Optional<Visitor> user = userRepository.findByEmail(username);
        if (user.isEmpty()) {
            throw new ReliefApplicationException("Visitor not found");
        }
        // Find addiction by name
        Addiction addiction = addictionRepository.findByName(addictionName)
                .orElseThrow(() -> new ReliefApplicationException("Addiction not found"));
        addictionRepository.delete(addiction);
    }

    public AddictionResponseDto getAddictionByName(String username, String addictionName) throws ReliefApplicationException {
        Optional<Visitor> OptionalUser = userRepository.findByEmail(username);
        Visitor user = OptionalUser.orElseThrow(() -> new ReliefApplicationException("Visitor not found"));
         Optional<Addiction> addiction = addictionRepository.findByUserAndName(user,addictionName);
         if (addiction.isEmpty()) {
             throw new ReliefApplicationException("Addiction not found");
         } else return addictionMapper.toDto(addiction.get());

    }

    public List<AddictionResponseDto> getAllUserAddictions(String username) throws ReliefApplicationException {
        Visitor user = userRepository.findByEmail(username)
                .orElseThrow(() -> new ReliefApplicationException("Visitor not found"));

        return addictionRepository.findByUser(user)
                .stream()
                .map(addictionMapper::toDto)
                .collect(Collectors.toList());
    }

    public AddictionResponseDto getAddictionDtoByIdAndUser(Long addictionId, String username) throws ReliefApplicationException {

        Visitor user = userRepository.findByEmail(username)
                .orElseThrow(() -> new ReliefApplicationException("Visitor not found"));

        Addiction addiction = addictionRepository.findByUserAndId(user, addictionId)
                .orElseThrow(() -> new ReliefApplicationException("Addiction not found"));
        return addictionMapper.toDto(addiction);
    }

    public Addiction getAddictionByIdAndUser(Long addictionId, String username) throws ReliefApplicationException {

        Visitor user = userRepository.findByEmail(username)
                .orElseThrow(() -> new ReliefApplicationException("Visitor not found"));

        return addictionRepository.findByUserAndId(user, addictionId)
                .orElseThrow(() -> new ReliefApplicationException("Addiction not found"));
    }
}
