package com.mohammad.relief.features.userSide.userTrigger.api;

import com.mohammad.relief.features.userSide.userTrigger.domain.UserTrigger;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TriggerMapper {
    @Mapping(target = "id", ignore = true)
    UserTrigger toEntity(UserTriggerRequestDto triggerDto);
    TriggerResponseDto toDto(UserTrigger trigger);
}
