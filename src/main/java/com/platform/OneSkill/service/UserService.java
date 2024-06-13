package com.platform.OneSkill.service;

import com.platform.OneSkill.dto.SignupRequest;

public interface UserService {
    boolean createUser(SignupRequest signupRequest);
}
