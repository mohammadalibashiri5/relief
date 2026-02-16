package com.mohammad.relief.features.adminSide.article.api;

import com.mohammad.relief.features.adminSide.article.domain.Article;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ArticleMapper {
    @Mapping(target = "id", ignore = true)
    Article toEntity(ArticleRequestDto request);
    @Mapping(target = "category", source = "category.name")
    ArticleResponseDto toDto(Article article);
}
