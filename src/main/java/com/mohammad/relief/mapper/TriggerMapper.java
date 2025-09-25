package com.mohammad.relief.mapper;

import com.mohammad.relief.data.dto.request.TriggerRequestDTO;
import com.mohammad.relief.data.dto.response.TriggerResponseDTO;
import com.mohammad.relief.data.entity.Trigger;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TriggerMapper {
    @Mapping(target = "id", ignore = true)
    Trigger toEntity(TriggerRequestDTO triggerDto);
    @Mapping(target = "addictionName", source = "userAddiction.adminAddiction.name")
    TriggerResponseDTO toDto(Trigger trigger);
}
