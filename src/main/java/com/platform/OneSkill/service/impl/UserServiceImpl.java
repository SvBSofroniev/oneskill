package com.platform.OneSkill.service.impl;

import com.platform.OneSkill.dto.UserDTO;
import com.platform.OneSkill.persistance.models.User;
import com.platform.OneSkill.persistance.repository.nonreactive.UserRepository;
import com.platform.OneSkill.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Base64;

import static com.platform.OneSkill.util.TimeUtil.getCurrentZonedDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    @Transactional
    @Override
    public boolean createUser(UserDTO dto) {
        try {
            User user = new User();
            user.setRole(dto.role());
            user.setPassword(Base64.getEncoder().encodeToString(dto.password().getBytes()));
            user.setFirstname(dto.firstname());
            user.setLastname(dto.lastname());
            user.setUsername(dto.username());
            user.setEmail(dto.email());
            user.setCreatedAt(getCurrentZonedDateTime());
            user.setUpdatedAt(getCurrentZonedDateTime());
            userRepository.save(user);
            return true;
        }catch (Exception e){
            log.error(e.getMessage());
            return false;
        }
    }
}
