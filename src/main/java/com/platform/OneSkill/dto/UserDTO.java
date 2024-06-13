package com.platform.OneSkill.dto;

public record UserDTO(
        String firstname,
        String lastname,
        String email,
        String username,
        String password,
        String role
) {}