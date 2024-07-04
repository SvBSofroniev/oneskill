package com.platform.OneSkill.dto;

public record UpdateUserDTO(
        String username,
        String firstname,
        String lastname,
        String password
) {
}
