package com.mohammad.relief.features.adminSide.aminAddiction.api;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record AdminAddictionRequestDto(
        @NotNull
        @NotEmpty
        String name,
        @NotNull
        @NotEmpty
        String imageUrl
) {
}
