package com.platform.OneSkill.security.dto;

import com.platform.OneSkill.dto.UserDTO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class UserInfoDetails implements UserDetails {
    private final String name;
    private final String password;
    private final Set<SimpleGrantedAuthority> authorities;

    public UserInfoDetails(UserDTO userDTO){
        name = userDTO.username();
        password = userDTO.password();
        authorities = new HashSet<>();
        authorities.addAll(userDTO.roles().stream().map(SimpleGrantedAuthority::new).toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return name;
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
