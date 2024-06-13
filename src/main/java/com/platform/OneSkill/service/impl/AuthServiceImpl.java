package com.platform.OneSkill.service.impl;

import com.platform.OneSkill.dto.SignupRequest;
import com.platform.OneSkill.persistance.models.User;
import com.platform.OneSkill.persistance.repository.nonreactive.UserRepository;
import com.platform.OneSkill.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.platform.OneSkill.util.TimeUtil.getCurrentZonedDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public boolean createUser(SignupRequest signupRequest) {
        if (userRepository.existsByUsernameAndEmail(signupRequest.username(),signupRequest.email())){
            return false;
        }

        try {
            User user = new User();
            user.setRole("user");
            user.setPassword(passwordEncoder.encode(signupRequest.password()));
            user.setFirstname(signupRequest.firstname());
            user.setLastname(signupRequest.lastname());
            user.setUsername(signupRequest.username());
            user.setEmail(signupRequest.email());
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
