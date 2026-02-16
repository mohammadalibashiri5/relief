package com.mohammad.relief.features.userSide.userTrigger.api;

import com.mohammad.relief.features.userSide.userTrigger.domain.UserTrigger;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TriggerMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(source = "sourceGlobalTriggerId", target = "sourceGlobalTrigger.id")
    UserTrigger toEntity(UserTriggerRequestDto triggerDto);
    @Mapping(source = "sourceGlobalTrigger.id", target = "sourceGlobalTriggerId")
    TriggerResponseDto toDto(UserTrigger trigger);
}
