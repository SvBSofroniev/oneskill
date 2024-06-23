package com.platform.OneSkill.service.impl;

import com.platform.OneSkill.dto.SignupRequest;
import com.platform.OneSkill.dto.UserDTO;
import com.platform.OneSkill.exception.UserNotFoundException;
import com.platform.OneSkill.persistance.models.User;
import com.platform.OneSkill.persistance.repository.UserRepository;
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

import java.util.Optional;
import java.util.Set;

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
    public boolean createUser(SignupRequest signupRequest) {
        if (userRepository.existsByUsernameAndEmail(signupRequest.username(),signupRequest.email())){
            return Boolean.FALSE;
        }

        try {
            User user = new User();
            user.setRoles(Set.of(RolesEnum.USER.getValue(), RolesEnum.DEV.getValue()));
            user.setPassword(encoder.encode(signupRequest.password()));
            user.setFirstname(signupRequest.firstname());
            user.setLastname(signupRequest.lastname());
            user.setUsername(signupRequest.username());
            user.setEmail(signupRequest.email());
            user.setCreatedAt(getCurrentZonedDateTime());
            user.setUpdatedAt(getCurrentZonedDateTime());
            userRepository.save(user);
            return Boolean.TRUE;
        }catch (Exception e){
            log.error(e.getMessage());
            return Boolean.FALSE;
        }
    }

    @Override
    public UserDTO findByUsername(String username) {
        Optional<User> foundUser = userRepository.findByUsername(username);
        return foundUser.map(UserMapper::mapModelToRecord)
                .orElseThrow(() -> new UserNotFoundException("User not found with username: " + username));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> foundUser = userRepository.findByUsername(username);
        return foundUser.map(user->new UserInfoDetails(mapModelToRecord(user)))
                .orElseThrow(()->new UserNotFoundException("User not found: " + username));
    }
}
