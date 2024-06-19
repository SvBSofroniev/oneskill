package com.platform.OneSkill.dto;

import java.util.Set;

public record UserDTO(
        String firstname,
        String lastname,
        String email,
        String username,
        String password,
        Set<String> roles,
        String createdAt,
        String updatedAt
) {}