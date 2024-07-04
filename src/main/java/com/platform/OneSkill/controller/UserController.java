package com.platform.OneSkill.controller;

import com.platform.OneSkill.dto.UpdateUserDTO;
import com.platform.OneSkill.dto.UserDTO;
import com.platform.OneSkill.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    public final UserService userService;

    @GetMapping("/{username}")
    public UserDTO getUserByUsername(@PathVariable String username){
        return userService.findByUsername(username);
    }

    @GetMapping
    public List<UserDTO> getAllUsers(){
        return userService.getAllUsers();
    }

    @PatchMapping("/{username}/roles/{role}")
    public void updateRoles(@PathVariable String username, @PathVariable String role){
        userService.updateRole(username, role);
    }

    @PostMapping("/{username}/update")
    public void updateUser(@PathVariable String username, @RequestBody UpdateUserDTO userDTO){
        userService.updateUser(username,userDTO);
    }
}
