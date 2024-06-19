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
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.core.io.Resource;
import reactor.core.publisher.Mono;

import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/videos")
public class VideoController {

    private final VideoService videoService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/upload")
    public Mono<Boolean> uploadVideo(@ModelAttribute VideoDTO videoDTO){
        Mono<Boolean> result = videoService.uploadVideo(videoDTO);
        return result;
    }

    @GetMapping(value = "/stream", produces = "video/mp4")
    public Mono<Resource> streamVideo(@RequestParam String username,
                                      @RequestParam String title) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals(RolesEnum.DEV.getValue()) ||
                a.getAuthority().equals(RolesEnum.USER.getValue()))) {
            return videoService.getVideoByUsernameAndTitle(username, title);
        } else {
            // Handle unauthorized access as needed
            throw new AccessDeniedException("Unauthorized access");
        }
    }

    @GetMapping("/hello")
    public String hello(){
        return "Hello";
    }
}
