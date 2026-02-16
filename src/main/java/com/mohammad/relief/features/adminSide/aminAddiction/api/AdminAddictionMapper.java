package com.mohammad.relief.features.adminSide.aminAddiction.api;

import com.mohammad.relief.features.adminSide.aminAddiction.domain.AdminAddiction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AdminAddictionMapper {
    @Mapping(target = "id", ignore = true)
    AdminAddiction toEntity(AdminAddictionRequestDto requestDto);

    @Mapping(target = "categoryType", source = "categoryType.name")
    AdminAddictionResponse toDto(AdminAddiction savedAddiction);
}
