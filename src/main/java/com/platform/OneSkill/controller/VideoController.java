package com.platform.OneSkill.controller;

import com.platform.OneSkill.dto.UserDTO;
import com.platform.OneSkill.dto.VideoDTO;
import com.platform.OneSkill.security.dto.UserInfoDetails;
import com.platform.OneSkill.service.UserService;
import com.platform.OneSkill.service.VideoService;
import com.platform.OneSkill.util.RolesEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.core.io.Resource;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/videos")
public class VideoController {

    private final VideoService videoService;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    @PostMapping("/upload")
    public Mono<Boolean> uploadVideo(@ModelAttribute VideoDTO videoDTO){
        Mono<Boolean> result = videoService.uploadVideo(videoDTO);
        return result;
    }

    @GetMapping(value = "/stream", produces = "video/mp4")
    public Mono<Resource> streamVideo(@RequestParam String username,
                                      @RequestParam String title) {

        UserDTO userDTO = userService.findByUsername(username);
        if (userDTO != null && userDTO.roles().contains(RolesEnum.DEV.getValue())){
            return videoService.getVideoByUsernameAndTitle(username, title);
        }else {
            throw new UsernameNotFoundException("Not found user");
        }
    }

    @GetMapping("/hello")
    public String hello(){
        return "Hello";
    }
}
