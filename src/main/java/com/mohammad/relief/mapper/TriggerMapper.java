package com.mohammad.relief.mapper;

import com.mohammad.relief.data.dto.request.TriggerRequestDTO;
import com.mohammad.relief.data.dto.response.TriggerResponseDTO;
import com.mohammad.relief.data.entity.Trigger;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TriggerMapper {
    Trigger toEntity(TriggerRequestDTO triggerDto);
    @Mapping(target = "userName", source = "user.name")
    @Mapping(target = "addictionName", source = "addiction.name")
    TriggerResponseDTO toDto(Trigger trigger);
}
