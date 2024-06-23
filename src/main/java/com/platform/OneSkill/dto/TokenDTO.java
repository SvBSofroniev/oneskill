package com.platform.OneSkill.dto;

public record TokenDTO(
        String token,
        String username,
        java.util.Collection<? extends org.springframework.security.core.GrantedAuthority> roles
) {
}
