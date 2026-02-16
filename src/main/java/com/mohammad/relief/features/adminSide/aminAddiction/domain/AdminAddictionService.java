package com.mohammad.relief.features.adminSide.aminAddiction.domain;

import com.mohammad.relief.features.adminSide.aminAddiction.api.AdminAddictionRequestDto;
import com.mohammad.relief.features.adminSide.aminAddiction.api.AdminAddictionResponse;
import com.mohammad.relief.features.userManagement.domain.Admin;
import com.mohammad.relief.features.adminSide.categoryTemplate.domain.CategoryType;
import com.mohammad.relief.common.exception.ReliefApplicationException;
import com.mohammad.relief.features.adminSide.aminAddiction.api.AdminAddictionMapper;
import com.mohammad.relief.features.adminSide.aminAddiction.data.AdminAddictionRepository;
import com.mohammad.relief.features.adminSide.categoryTemplate.domain.CategoryTypeService;
import com.mohammad.relief.features.userManagement.domain.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminAddictionService {

    private static final String ADDICTION_NOT_FOUND = "Addiction not found";
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

    public Optional<AdminAddiction> findById(Long addictionId) {
        return  addictionRepository.findById(addictionId);
    }
}
