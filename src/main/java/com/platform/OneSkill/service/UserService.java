package com.platform.OneSkill.service;

import com.platform.OneSkill.dto.SignupRequest;
import com.platform.OneSkill.dto.UserDTO;

public interface UserService {
    String createUser(SignupRequest signupRequest);
}
