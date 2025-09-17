package com.mohammad.relief.service;

import com.mohammad.relief.data.dto.request.UserAddictionRequestDto;
import com.mohammad.relief.data.dto.response.AdminAddictionResponse;
import com.mohammad.relief.data.dto.response.UserAddictionResponseDto;
import com.mohammad.relief.data.entity.UserAddiction;
import com.mohammad.relief.data.entity.Visitor;
import com.mohammad.relief.data.entity.addiction.AdminAddiction;
import com.mohammad.relief.exception.ReliefApplicationException;
import com.mohammad.relief.mapper.UserAddictionMapper;
import com.mohammad.relief.repository.AdminAddictionRepository;
import com.mohammad.relief.repository.UserAddictionRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.mohammad.relief.service.AddictionService.ADDICTION_NOT_FOUND_MESSAGE;

@Service
@RequiredArgsConstructor
public class UserAddictionService {
    private final UserService userService;
    private final AdminAddictionService adminAddictionService;
    private final AdminAddictionRepository adminAddictionRepository;
    private final UserAddictionRepository userAddictionRepository;
    private final UserAddictionMapper userAddictionMapper;


    public UserAddictionResponseDto createUserAddiction(UserAddictionRequestDto req, String addictionName, String email) throws ReliefApplicationException {
        Visitor user = userService.findByEmail(email);
        Optional<AdminAddiction> adminAddiction = adminAddictionRepository.findByName(addictionName);
        if (adminAddiction.isEmpty()) {
            throw new ReliefApplicationException("No such an addiction with this name");
        }
        if (Boolean.TRUE.equals(userAddictionRepository.existsByAddiction_NameAndUser(addictionName, user))){
            throw new ReliefApplicationException("This user already has this addiction");
        }
        UserAddiction addiction = userAddictionMapper.toEntity(req);
        addiction.setAddiction(adminAddiction.get());
        addiction.setUser(user);
        UserAddiction savedAddiction = userAddictionRepository.save(addiction);
        return userAddictionMapper.toDto(savedAddiction);
    }

    public List<UserAddictionResponseDto> getAllUserAddiction(String email) throws ReliefApplicationException {
        Visitor user = userService.findByEmail(email);
        return userAddictionRepository.findByUser(user).stream().map(userAddictionMapper::toDto).toList();
    }
    public List<AdminAddictionResponse> getAllAddictionsByCategoryType(String categoryType) throws ReliefApplicationException {
        return adminAddictionService.getAddictionByCategoryName(categoryType);
    }


    public UserAddictionResponseDto updateAddictionByIdAndUser(Long id, @Valid UserAddictionRequestDto addictionRequestDto, String email) throws ReliefApplicationException {

        UserAddiction addiction = findAddictionByIdAndValidateOwnership(id, email);

        boolean isAddictionUpdate = false;


        if (addictionRequestDto.yearsOfAddiction() != null) {
            addiction.setYearsOfAddiction(addictionRequestDto.yearsOfAddiction());
            isAddictionUpdate = true;
        }
        if (addictionRequestDto.severityLevel() != null) {
            addiction.setSeverityLevel(addictionRequestDto.severityLevel());
            isAddictionUpdate = true;
        }

        if (isAddictionUpdate) {
            userAddictionRepository.save(addiction);
        }

        return userAddictionMapper.toDto(addiction);

    }

    public void deleteAddictionByIdAndUser(Long addictionId, String email) throws ReliefApplicationException {
        UserAddiction addiction = findAddictionByIdAndValidateOwnership(addictionId, email);
        userAddictionRepository.delete(addiction);
    }

    /**
     * Finds a user addiction by ID and validates that it belongs to the user with given email.
     * @throws ReliefApplicationException if addiction not found or doesn't belong to user
     */
    private UserAddiction findAddictionByIdAndValidateOwnership(Long addictionId, String email) throws ReliefApplicationException {
        Visitor user = userService.findByEmail(email);
        Optional<UserAddiction> addiction = userAddictionRepository.findByIdAndUser(addictionId, user);
        if (addiction.isEmpty()) {
            throw new ReliefApplicationException(ADDICTION_NOT_FOUND_MESSAGE);
        }
        return addiction.get();
    }
}
