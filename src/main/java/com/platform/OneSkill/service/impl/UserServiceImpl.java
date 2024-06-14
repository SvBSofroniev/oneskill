package com.platform.OneSkill.service.impl;

import com.platform.OneSkill.dto.SignupRequest;
import com.platform.OneSkill.dto.UserDTO;
import com.platform.OneSkill.exception.UserNotFoundException;
import com.platform.OneSkill.persistance.models.User;
import com.platform.OneSkill.persistance.repository.nonreactive.UserRepository;
import com.platform.OneSkill.security.dto.UserInfoDetails;
import com.platform.OneSkill.service.UserService;
import com.platform.OneSkill.util.RolesEnum;
import com.platform.OneSkill.util.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.platform.OneSkill.util.TimeUtil.getCurrentZonedDateTime;
import static com.platform.OneSkill.util.mapper.UserMapper.mapModelToRecord;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserDetailsService, UserService {
    private final UserRepository userRepository;

    private final PasswordEncoder encoder;

    @Transactional
    @Override
    public String createUser(SignupRequest signupRequest) {
        if (userRepository.existsByUsernameAndEmail(signupRequest.username(),signupRequest.email())){
            return "User wasn't created.";
        }

        try {
            User user = new User();
            user.setRoles(List.of(RolesEnum.USER.name));
            user.setPassword(encoder.encode(signupRequest.password()));
            user.setFirstname(signupRequest.firstname());
            user.setLastname(signupRequest.lastname());
            user.setUsername(signupRequest.username());
            user.setEmail(signupRequest.email());
            user.setCreatedAt(getCurrentZonedDateTime());
            user.setUpdatedAt(getCurrentZonedDateTime());
            userRepository.save(user);
            return "User was created.";
        }catch (Exception e){
            log.error(e.getMessage());
            return "User wasn't created.";
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> foundUser = userRepository.findByUsername(username);
        return foundUser.map(user->new UserInfoDetails(mapModelToRecord(user)))
                .orElseThrow(()->new UserNotFoundException("User not found: " + username));
    }
}
