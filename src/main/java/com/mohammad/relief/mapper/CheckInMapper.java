package com.mohammad.relief.mapper;

import com.mohammad.relief.data.dto.request.CheckInRequestDto;
import com.mohammad.relief.data.dto.response.CheckInResponseDto;
import com.mohammad.relief.data.entity.CheckIn;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CheckInMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user.username", source = "userName") // Assuming 'username' exists in Visitor
    @Mapping(target = "addiction.name", source = "addictionName")// We'll set addiction manually
    CheckIn toEntity(CheckInRequestDto checkInRequestDto);

    @Mapping(target = "userName", source = "user.username") // Assuming 'username' exists in Visitor
    @Mapping(target = "addictionName", source = "addiction.name")
    CheckInResponseDto toDto(CheckIn checkIn);
}
