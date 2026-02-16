package com.mohammad.relief.features.userSide.userAddiction.domain;

import com.mohammad.relief.features.userSide.userAddiction.api.UserAddictionRequestDto;
import com.mohammad.relief.features.adminSide.aminAddiction.api.AdminAddictionResponse;
import com.mohammad.relief.features.userSide.userAddiction.api.UserAddictionResponseDto;
import com.mohammad.relief.features.userManagement.domain.Visitor;
import com.mohammad.relief.features.adminSide.aminAddiction.domain.AdminAddiction;
import com.mohammad.relief.common.exception.ReliefApplicationException;
import com.mohammad.relief.features.adminSide.aminAddiction.domain.AdminAddictionService;
import com.mohammad.relief.features.userSide.userAddiction.api.UserAddictionMapper;
import com.mohammad.relief.features.adminSide.aminAddiction.data.AdminAddictionRepository;
import com.mohammad.relief.features.userSide.userAddiction.data.UserAddictionRepository;
import com.mohammad.relief.features.userManagement.domain.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserAddictionService {
    private final UserService userService;
    private final AdminAddictionService adminAddictionService;
    private final AdminAddictionRepository adminAddictionRepository;
    private final UserAddictionRepository userAddictionRepository;
    private final UserAddictionMapper userAddictionMapper;
    String ADDICTION_NOT_FOUND_MESSAGE = "Addiction Not Found";


    public UserAddictionResponseDto createUserAddiction(UserAddictionRequestDto req, String addictionName, String email) throws ReliefApplicationException {
        Visitor user = userService.findByEmail(email);
        Optional<AdminAddiction> adminAddiction = adminAddictionRepository.findByName(addictionName);
        if (adminAddiction.isEmpty()) {
            throw new ReliefApplicationException("No such an addiction with this name");
        }
        if (Boolean.TRUE.equals(userAddictionRepository.existsByAdminAddiction_NameAndUser(addictionName, user))){
            throw new ReliefApplicationException("This user already has this addiction");
        }
        UserAddiction addiction = userAddictionMapper.toEntity(req);
        addiction.setAdminAddiction(adminAddiction.get());
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


        if (addictionRequestDto.daysPerWeek() != addiction.getDaysPerWeek()) {
            addiction.setDaysPerWeek(addictionRequestDto.daysPerWeek());
            isAddictionUpdate = true;
        }
        if (addictionRequestDto.frequencyPerDay() != addiction.getFrequencyPerDay()) {
            addiction.setFrequencyPerDay(addictionRequestDto.frequencyPerDay());
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
    public UserAddiction findAddictionByIdAndValidateOwnership(Long addictionId, String email) throws ReliefApplicationException {
        Visitor user = userService.findByEmail(email);
        Optional<UserAddiction> addiction = userAddictionRepository.findByIdAndUser(addictionId, user);
        if (addiction.isEmpty()) {
            throw new ReliefApplicationException(ADDICTION_NOT_FOUND_MESSAGE);
        }
        return addiction.get();
    }
}
