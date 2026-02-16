package com.mohammad.relief.features.userSide.userAddiction.api;

import com.mohammad.relief.features.userSide.userAddiction.domain.UserAddiction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserAddictionMapper {
    UserAddiction toEntity(UserAddictionRequestDto requestDto);
    @Mapping(target = "addictionName" , source = "adminAddiction.name")
    @Mapping(target = "addictionImageUrl" , source = "adminAddiction.imageUrl")
    @Mapping(target = "categoryType" , source = "adminAddiction.categoryType.name")
    UserAddictionResponseDto toDto(UserAddiction userAddiction);
}
