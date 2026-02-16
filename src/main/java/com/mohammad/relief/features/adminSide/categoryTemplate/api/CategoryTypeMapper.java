package com.mohammad.relief.features.adminSide.categoryTemplate.api;

import com.mohammad.relief.features.adminSide.categoryTemplate.domain.CategoryType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryTypeMapper {
    CategoryType toEntity(CategoryTypeRequestDto requestDto);
    CategoryTypeResponseDto toDto(CategoryType categoryType);
}
