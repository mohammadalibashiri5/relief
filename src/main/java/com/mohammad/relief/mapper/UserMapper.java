package com.mohammad.relief.mapper;

import com.mohammad.relief.data.dto.request.UserRequestDto;
import com.mohammad.relief.data.dto.response.UserResponseDto;
import com.mohammad.relief.data.entity.Visitor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponseDto toResponseDto(Visitor visitor);

    // Convert UserRequestDto to Visitor entity
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Visitor toEntity(UserRequestDto userRequestDto);
}
