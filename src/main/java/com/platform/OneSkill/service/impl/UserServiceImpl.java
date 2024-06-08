package com.platform.OneSkill.service.impl;

import com.platform.OneSkill.dto.UserDTO;
import com.platform.OneSkill.persistance.models.User;
import com.platform.OneSkill.persistance.repository.UserRepository;
import com.platform.OneSkill.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Base64;
import java.util.Date;

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
            user.setRole(dto.getRole());
            user.setPassword(Base64.getEncoder().encodeToString(dto.getPassword().getBytes()));

            System.out.printf(user.getPassword());
            log.info(Base64.getEncoder().encodeToString(dto.getPassword().getBytes()));

            user.setFirstname(dto.getFirstname());
            user.setLastname(dto.getLastname());
            user.setUsername(dto.getUsername());
            user.setEmail(dto.getEmail());
            user.setCreatedAt(Date.from(Instant.now()));
            user.setUpdatedAt(Date.from(Instant.now()));
            userRepository.save(user);
            return true;
        }catch (Exception e){
            log.error(e.getMessage());
            return false;
        }
    }
}
