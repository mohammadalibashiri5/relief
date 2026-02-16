package com.mohammad.relief.features.adminSide.article.api;

import java.time.LocalDateTime;

public record ArticleResponseDto(
        Long id,
        String title,
        String content,
        String imageUrl,
        String category,
        LocalDateTime createdAt
) {
}
