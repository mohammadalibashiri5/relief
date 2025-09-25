package com.mohammad.relief.mapper;

import com.mohammad.relief.data.dto.request.UserAddictionRequestDto;
import com.mohammad.relief.data.dto.response.UserAddictionResponseDto;
import com.mohammad.relief.data.entity.UserAddiction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserAddictionMapper {
    UserAddiction toEntity(UserAddictionRequestDto requestDto);
    @Mapping(target = "addictionName" , source = "adminAddiction.name")
    @Mapping(target = "categoryType" , source = "adminAddiction.categoryType.name")
    UserAddictionResponseDto toDto(UserAddiction userAddiction);
}
