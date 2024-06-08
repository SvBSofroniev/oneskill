package com.platform.OneSkill.controller;

import com.platform.OneSkill.dto.UserDTO;
import com.platform.OneSkill.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody UserDTO userDto) {

        return userService.createUser(userDto)
        ? ResponseEntity.status(HttpStatus.CREATED).body("User has been created.")
        : ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User hasn't been created.");
    }
}
