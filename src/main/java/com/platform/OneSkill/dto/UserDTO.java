package com.platform.OneSkill.dto;

import java.util.List;

public record UserDTO(
        String firstname,
        String lastname,
        String email,
        String username,
        String password,
        List<String> roles,
        String createdAt,
        String updatedAt
) {}