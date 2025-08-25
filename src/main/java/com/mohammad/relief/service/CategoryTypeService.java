package com.mohammad.relief.service;

import com.mohammad.relief.data.dto.request.CategoryTypeRequestDto;
import com.mohammad.relief.data.dto.response.CategoryTypeResponseDto;
import com.mohammad.relief.data.entity.Admin;
import com.mohammad.relief.data.entity.CategoryType;
import com.mohammad.relief.exception.ReliefApplicationException;
import com.mohammad.relief.mapper.CategoryTypeMapper;
import com.mohammad.relief.repository.CategoryTypeRepository;
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
