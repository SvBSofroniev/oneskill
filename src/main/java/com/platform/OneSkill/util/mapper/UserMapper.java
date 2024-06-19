package com.platform.OneSkill.util.mapper;

import com.platform.OneSkill.dto.UserDTO;
import com.platform.OneSkill.persistance.models.User;

public class UserMapper {

    public static UserDTO mapModelToRecord(User user){
        return new UserDTO(
                user.getFirstname(),
                user.getLastname(),
                user.getEmail(),
                user.getUsername(),
                user.getPassword(),
                user.getRoles(),
                user.getCreatedAt(),
                user.getUpdatedAt());
    }
}
