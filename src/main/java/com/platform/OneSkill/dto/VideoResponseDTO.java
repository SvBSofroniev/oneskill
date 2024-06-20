package com.platform.OneSkill.dto;

import java.io.InputStream;

public record VideoResponseDTO(
        String title,
        String description,
        InputStream stream
) {
}
