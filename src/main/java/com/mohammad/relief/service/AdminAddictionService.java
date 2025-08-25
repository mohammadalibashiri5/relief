package com.mohammad.relief.service;

import com.mohammad.relief.data.dto.request.AdminAddictionRequestDto;
import com.mohammad.relief.data.dto.response.AdminAddictionResponse;
import com.mohammad.relief.data.entity.Admin;
import com.mohammad.relief.data.entity.CategoryType;
import com.mohammad.relief.data.entity.addiction.AdminAddiction;
import com.mohammad.relief.exception.ReliefApplicationException;
import com.mohammad.relief.mapper.AdminAddictionMapper;
import com.mohammad.relief.repository.AdminAddictionRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminAddictionService {

    private static final String ADDICTION_NOT_FOUND = "Cannot modify: addiction not found";
    private static final String UNAUTHORIZED_MODIFICATION =
            "Unauthorized: Only the creator can modify this addiction. Please contact the addiction creator for modifications.";

    private final UserService userService;
    private final AdminAddictionRepository addictionRepository;
    private final AdminAddictionMapper addictionMapper;
    private final CategoryTypeService categoryTypeService;

    public AdminAddictionResponse createAddiction(AdminAddictionRequestDto requestDto, String categoryTypeName, String email) throws ReliefApplicationException {
        Admin admin = userService.findAdminByEmail(email);
        CategoryType categoryType = categoryTypeService.findByName(categoryTypeName, admin.getEmail());
        AdminAddiction addiction = addictionMapper.toEntity(requestDto);
        addiction.setCategoryType(categoryType);
        addiction.setAdmin(admin);
        AdminAddiction savedAddiction = addictionRepository.save(addiction);
        return addictionMapper.toDto(savedAddiction);

    }

    public List<AdminAddictionResponse> getAddictionByCategoryName(String categoryName) throws ReliefApplicationException {
        List<AdminAddiction> adminAddiction = addictionRepository.findByCategoryType_Name(categoryName);
        if (adminAddiction.isEmpty()) {
            throw new ReliefApplicationException("No such an addiction with this category type");
        }
        return adminAddiction.stream()
                .map(addictionMapper::toDto)
                .toList();
    }

    public AdminAddictionResponse updateAdminAddictionById(Long addictionId,
                                                           @Valid AdminAddictionRequestDto requestDto, String email) throws ReliefApplicationException {
        AdminAddiction addiction = findAddictionAndValidateAdmin(addictionId, email);

        boolean isUpdated = false;
        if (requestDto.name() != null) {
            addiction.setName(requestDto.name());
            isUpdated = true;
        }
        if (requestDto.imageUrl() != null) {
            addiction.setImageUrl(requestDto.imageUrl());
            isUpdated = true;
        }
        if (isUpdated) {
            addictionRepository.save(addiction);
        }
        return addictionMapper.toDto(addiction);
    }

    public void deleteAdminAddictionById(Long addictionId, String email)
            throws ReliefApplicationException {
        AdminAddiction addiction = findAddictionAndValidateAdmin(addictionId, email);
        addictionRepository.delete(addiction);
    }

    private AdminAddiction findAddictionAndValidateAdmin(Long addictionId, String email)
            throws ReliefApplicationException {
        Admin admin = userService.findAdminByEmail(email);
        AdminAddiction addiction = addictionRepository.findById(addictionId)
                .orElseThrow(() -> new ReliefApplicationException(ADDICTION_NOT_FOUND));

        if (!addiction.getAdmin().getId().equals(admin.getId())) {
            throw new ReliefApplicationException(UNAUTHORIZED_MODIFICATION);
        }

        return addiction;
    }

    public List<AdminAddictionResponse> getAllAddictions() {
        List<AdminAddiction> addictions = addictionRepository.findAll();
        return addictions.stream()
                .map(addictionMapper::toDto)
                .toList();
    }
}
