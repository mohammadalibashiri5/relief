package com.mohammad.relief.mapper;

import com.mohammad.relief.data.dto.request.SolutionRequestDto;
import com.mohammad.relief.data.dto.response.SolutionResponseDto;
import com.mohammad.relief.data.entity.Solution;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SolutionMapper {
    @Mapping(target = "id", ignore = true)
    Solution toEntity(SolutionRequestDto requestDto);
    SolutionResponseDto toDto(Solution solution);

}
