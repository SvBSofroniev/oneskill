package com.platform.OneSkill.service;

import com.platform.OneSkill.dto.SignupRequest;
import com.platform.OneSkill.dto.UserDTO;

import java.util.Optional;

public interface UserService {
    boolean createUser(SignupRequest signupRequest);

    UserDTO findByUsername(String username);
}
