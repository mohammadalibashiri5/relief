package com.mohammad.relief.features.userManagement.api;

import com.mohammad.relief.features.userManagement.domain.Admin;
import com.mohammad.relief.features.userManagement.domain.Visitor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponseDto toResponseDto(Visitor visitor);
    UserResponseDto toAdminResponseDto(Admin admin);

    // Convert UserRequestDto to Visitor entity
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Visitor toEntity(UserRequestDto userRequestDto);
    Admin toAdmin(UserRequestDto userRequestDto);

}
