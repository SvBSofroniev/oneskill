package com.platform.OneSkill.dto;

import java.util.List;

public record TokenDTO(
        String token,
        String username,
        List<String> roles
) {
}
