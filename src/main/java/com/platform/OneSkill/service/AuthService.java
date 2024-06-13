package com.platform.OneSkill.service;

import com.platform.OneSkill.dto.SignupRequest;

public interface AuthService {
    boolean createUser(SignupRequest signupRequest);
}
