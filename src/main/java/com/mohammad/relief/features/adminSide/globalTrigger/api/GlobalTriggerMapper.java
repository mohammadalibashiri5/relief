package com.mohammad.relief.features.adminSide.globalTrigger.api;

import com.mohammad.relief.features.adminSide.globalTrigger.domain.GlobalTrigger;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface GlobalTriggerMapper {
    @Mapping(target = "addictionName", source = "adminAddiction.name")
    GlobalTriggerResponseDto toDto(GlobalTrigger globalTrigger);
    GlobalTrigger toEntity(GlobalTriggerReqDto dto);

}
