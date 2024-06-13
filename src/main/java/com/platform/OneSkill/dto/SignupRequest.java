package com.platform.OneSkill.dto;

public record SignupRequest(
    String firstname,
    String lastname,
    String email,
    String username,
    String password
) {}
