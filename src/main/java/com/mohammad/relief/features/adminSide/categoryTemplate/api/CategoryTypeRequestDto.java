package com.mohammad.relief.features.adminSide.categoryTemplate.api;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record CategoryTypeRequestDto(
        @NotNull @NotEmpty
        String name,
        @NotNull @NotEmpty
        String imageUrl
) {
}
