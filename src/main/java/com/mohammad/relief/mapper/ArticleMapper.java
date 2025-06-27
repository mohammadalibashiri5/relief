package com.mohammad.relief.mapper;

import com.mohammad.relief.data.dto.request.ArticleRequestDto;
import com.mohammad.relief.data.dto.response.ArticleResponseDto;
import com.mohammad.relief.data.entity.Article;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ArticleMapper {
    @Mapping(target = "id", ignore = true)
    Article toEntity(ArticleRequestDto request);
    ArticleResponseDto toDto(Article article);
}
