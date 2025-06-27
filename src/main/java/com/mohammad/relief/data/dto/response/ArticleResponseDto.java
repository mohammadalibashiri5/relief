package com.mohammad.relief.data.dto.response;

import java.time.LocalDateTime;

public record ArticleResponseDto(
        String title,
        String content,
        String imageUrl,
        String category,
        LocalDateTime createdAt
) {
}
