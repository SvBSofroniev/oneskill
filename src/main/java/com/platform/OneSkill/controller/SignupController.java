package com.platform.OneSkill.controller;

import com.platform.OneSkill.dto.SignupRequest;
import com.platform.OneSkill.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/signup")
public class SignupController {


    private final UserService userService;

    @PostMapping
    public ResponseEntity<String> signupCustomer(@RequestBody SignupRequest signupRequest){
        boolean isUserCreated = userService.createUser(signupRequest);

        return isUserCreated ? ResponseEntity.status(HttpStatus.CREATED).body("User created.") :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to create user.");

    }
}
