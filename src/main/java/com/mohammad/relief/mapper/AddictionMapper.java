package com.mohammad.relief.mapper;

import com.mohammad.relief.data.dto.AddictionRequestDto;
import com.mohammad.relief.data.dto.AddictionResponseDto;
import com.mohammad.relief.data.entity.Addiction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface AddictionMapper {

    Addiction toEntity(AddictionRequestDto dto);
    AddictionResponseDto toDto(Addiction entity);
}


