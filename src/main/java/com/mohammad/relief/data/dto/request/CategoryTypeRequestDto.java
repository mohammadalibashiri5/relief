package com.mohammad.relief.data.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record CategoryTypeRequestDto(
        @NotNull @NotEmpty
        String name,
        @NotNull @NotEmpty
        String imageUrl
) {
}
