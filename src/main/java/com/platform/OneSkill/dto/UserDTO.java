package com.platform.OneSkill.dto;

import java.util.List;

public record UserDTO(
        String firstname,
        String lastname,
        String email,
        String username,
        List<String> role,
        String createdAt,
        String updatedAt
) {}