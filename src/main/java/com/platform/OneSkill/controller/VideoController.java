package com.platform.OneSkill.controller;

import com.platform.OneSkill.dto.VideoDTO;
import com.platform.OneSkill.dto.VideoResponseDTO;
import com.platform.OneSkill.service.VideoService;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "http://localhost:4200")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/videos")
public class VideoController {

    private final VideoService videoService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/upload")
    public boolean uploadVideo(@ModelAttribute VideoDTO videoDTO) throws IOException {
        return videoService.uploadVideo(videoDTO);
    }

    @GetMapping(value = "/stream")
    public void streamVideo(@RequestParam String id,  HttpServletResponse response) throws IOException {
       VideoResponseDTO foundVideo = videoService.getVideo(id);
        FileCopyUtils.copy(foundVideo.stream(), response.getOutputStream());
    }

    @GetMapping("/hello")
    public String hello(){
        return "Hello";
    }
}
