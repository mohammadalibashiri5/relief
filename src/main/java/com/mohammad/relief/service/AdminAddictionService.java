package com.mohammad.relief.service;

import com.mohammad.relief.data.dto.request.AdminAddictionRequestDto;
import com.mohammad.relief.data.dto.response.AdminAddictionResponse;
import com.mohammad.relief.data.entity.Addiction;
import com.mohammad.relief.data.entity.Admin;
import com.mohammad.relief.data.entity.CategoryType;
import com.mohammad.relief.data.entity.addiction.AdminAddiction;
import com.mohammad.relief.exception.ReliefApplicationException;
import com.mohammad.relief.mapper.AddictionMapper;
import com.mohammad.relief.mapper.AdminAddictionMapper;
import com.mohammad.relief.repository.AddictionRepository;
import com.mohammad.relief.repository.AdminAddictionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminAddictionService {
    private final UserService userService;
    private final AdminAddictionRepository addictionRepository;
    private final AdminAddictionMapper addictionMapper;
    private final CategoryTypeService categoryTypeService;

    public AdminAddictionResponse createAddiction(AdminAddictionRequestDto requestDto, String categoryTypeName, String email) throws ReliefApplicationException {
        Admin admin = userService.findAdminByEmail(email);
        CategoryType categoryType = categoryTypeService.findByName(categoryTypeName,admin.getEmail());
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
}
