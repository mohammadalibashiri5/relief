package com.mohammad.relief.mapper;

import com.mohammad.relief.data.dto.request.AddictionRequestDto;
import com.mohammad.relief.data.dto.response.AddictionResponseDto;
import com.mohammad.relief.data.entity.Addiction;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface AddictionMapper {

    Addiction toEntity(AddictionRequestDto dto);
    AddictionResponseDto toDto(Addiction entity);
}


