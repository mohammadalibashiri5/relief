package com.mohammad.relief.data.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record ArticleRequestDto(
        @NotNull @NotEmpty
        String title,
        @NotNull @NotEmpty
        String content,
        @NotNull @NotEmpty
        String imageUrl,
        @NotNull @NotEmpty
        String category
) {
}
