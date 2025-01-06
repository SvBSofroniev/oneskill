package com.platform.OneSkill.service;

import com.platform.OneSkill.dto.SignupRequest;
import com.platform.OneSkill.dto.UpdateUserDTO;
import com.platform.OneSkill.dto.UserDTO;

import java.util.List;

public interface UserService {
    boolean createUser(SignupRequest signupRequest);

    UserDTO findByUsername(String username);

    List<UserDTO> getAllUsers();

    void updateRole(String username, String role);

    void updateUser(String username, UpdateUserDTO userDTO);

    Boolean updatePassword(String email, String password);
}
