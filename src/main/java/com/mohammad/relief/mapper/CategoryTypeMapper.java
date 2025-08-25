package com.mohammad.relief.mapper;

import com.mohammad.relief.data.dto.request.CategoryTypeRequestDto;
import com.mohammad.relief.data.dto.response.CategoryTypeResponseDto;
import com.mohammad.relief.data.entity.CategoryType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryTypeMapper {
    CategoryType toEntity(CategoryTypeRequestDto requestDto);
    CategoryTypeResponseDto toDto(CategoryType categoryType);
}
