package com.platform.OneSkill.service.impl;

import com.platform.OneSkill.dto.SignupRequest;
import com.platform.OneSkill.dto.UpdateUserDTO;
import com.platform.OneSkill.dto.UserDTO;
import com.platform.OneSkill.exception.UserNotFoundException;
import com.platform.OneSkill.persistance.models.User;
import com.platform.OneSkill.persistance.repository.UserRepository;
import com.platform.OneSkill.security.dto.UserInfoDetails;
import com.platform.OneSkill.service.EmailService;
import com.platform.OneSkill.service.UserService;
import com.platform.OneSkill.util.RolesEnum;
import com.platform.OneSkill.util.TimeUtil;
import com.platform.OneSkill.util.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.management.relation.Role;
import java.util.ArrayList;
import java.util.List;
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
    private final EmailService emailService;


    @Transactional
    @Override
    public boolean createUser(SignupRequest signupRequest) {
        if (userRepository.existsByUsernameAndEmail(signupRequest.username(),signupRequest.email())){
            return Boolean.FALSE;
        }

        try {
            User user = new User();
            user.setRoles(Set.of(RolesEnum.USER.getValue()));
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
    public List<UserDTO> getAllUsers() {
        List<User> foundUsers = userRepository.findAll();
        if (!foundUsers.isEmpty()) {
            return foundUsers.stream().map(UserMapper::mapModelToRecord).toList();
        }
        return new ArrayList<>();
    }

    @Transactional
    @Override
    public void updateRole(String username, String role) {
        String message;
        RolesEnum roleEnum = RolesEnum.fromString(role);
        if (RolesEnum.isValidRole(role)){
            Optional<User> foundUser = userRepository.findByUsername(username);

            foundUser.ifPresent(user ->{
                assert roleEnum != null;
                if (user.getRoles().contains(roleEnum.getValue())) {
                    user.getRoles().remove(roleEnum.getValue());
                }else {
                    user.getRoles().add(roleEnum.getValue());
                }
                userRepository.save(user);
            });
        }
    }

    @Override
    public void updateUser(String username, UpdateUserDTO userDTO) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            if (userDTO.firstname() != null && !userDTO.firstname().isEmpty()) {
                user.setFirstname(userDTO.firstname());
            }
            if (userDTO.lastname() != null && !userDTO.lastname().isEmpty()) {
                user.setLastname(userDTO.lastname());
            }
            if (userDTO.password() != null && !userDTO.password().isEmpty()) {
                String encodedPassword = encoder.encode(userDTO.password());
                user.setPassword(encodedPassword);
            }

            user.setUpdatedAt(TimeUtil.getCurrentZonedDateTime());

            String email = userRepository.save(user).getEmail();

            sendUpdateNotificationEmail(user.getUsername(), email);
        } else {
            log.error("User not found with username: {}", username);
        }
    }


    private void sendUpdateNotificationEmail(String username, String email) {

        String subject = "User Profile Updated";
        String text = "Hello,\n\nUser " + username + "'s profile has been updated successfully.";

        emailService.sendSimpleMessage(email, subject, text);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> foundUser = userRepository.findByUsername(username);
        return foundUser.map(user->new UserInfoDetails(mapModelToRecord(user)))
                .orElseThrow(()->new UserNotFoundException("User not found: " + username));
    }
}
