package com.mohammad.relief.features.adminSide.categoryTemplate.domain;

import com.mohammad.relief.features.adminSide.categoryTemplate.api.CategoryTypeRequestDto;
import com.mohammad.relief.features.adminSide.categoryTemplate.api.CategoryTypeResponseDto;
import com.mohammad.relief.features.userManagement.domain.Admin;
import com.mohammad.relief.common.exception.ReliefApplicationException;
import com.mohammad.relief.features.adminSide.categoryTemplate.api.CategoryTypeMapper;
import com.mohammad.relief.features.adminSide.categoryTemplate.data.CategoryTypeRepository;
import com.mohammad.relief.features.userManagement.domain.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryTypeService {
    private final CategoryTypeRepository categoryTypeRepository;
    private final CategoryTypeMapper categoryTypeMapper;
    private final UserService userService;

    public CategoryTypeResponseDto addCategoryType(CategoryTypeRequestDto requestDto, String email) throws ReliefApplicationException {
        Admin admin = userService.findAdminByEmail(email);
        CategoryType categoryType = categoryTypeMapper.toEntity(requestDto);
        categoryType.setAdmin(admin);
        CategoryType savedCategoryType = categoryTypeRepository.save(categoryType);
        return categoryTypeMapper.toDto(savedCategoryType);

    }
    public List<CategoryTypeResponseDto> categoryTypes()  {
        List<CategoryType> categoryTypes = categoryTypeRepository.findAll();
        return categoryTypes.stream()
                .map(categoryTypeMapper::toDto)
                .toList();
    }

    public void deleteCategoryTypeById(Integer id, String email) throws ReliefApplicationException {
        Admin admin = userService.findAdminByEmail(email);
        Optional<CategoryType> categoryType = categoryTypeRepository.findById(id);
        if (categoryType.isEmpty()) {
            throw new ReliefApplicationException("Category type not found");
        }
        if (categoryType.get().getAdmin().getId() != admin.getId()) {
            throw new ReliefApplicationException("You don't have the permission to delete this category type");
        }
        categoryTypeRepository.delete(categoryType.get());
    }

    public CategoryType findByName(String categoryTypeName, String email) throws ReliefApplicationException {
        Admin admin = userService.findAdminByEmail(email);
        Optional<CategoryType> categoryType = categoryTypeRepository.findByNameAndAdmin(categoryTypeName, admin);
        if (categoryType.isEmpty()) {
            throw new ReliefApplicationException("Category type not found");
        }
        return categoryType.get();
    }
}
