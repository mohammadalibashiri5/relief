package com.mohammad.relief.mapper;

import com.mohammad.relief.data.dto.response.CheckInResponseDto;
import com.mohammad.relief.data.entity.CheckIn;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CheckInMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    CheckIn toEntity(CheckInResponseDto checkInResponseDto);
    CheckInResponseDto toDto(CheckIn checkIn);

}
