package com.mohammad.relief.mapper;

import com.mohammad.relief.data.dto.request.AdminAddictionRequestDto;
import com.mohammad.relief.data.dto.response.AdminAddictionResponse;
import com.mohammad.relief.data.entity.addiction.AdminAddiction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AdminAddictionMapper {
    @Mapping(target = "id", ignore = true)
    AdminAddiction toEntity(AdminAddictionRequestDto requestDto);

    @Mapping(target = "categoryType", source = "categoryType.name")
    AdminAddictionResponse toDto(AdminAddiction savedAddiction);
}
