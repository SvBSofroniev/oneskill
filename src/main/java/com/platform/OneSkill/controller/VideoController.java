package com.platform.OneSkill.controller;

import com.platform.OneSkill.dto.VideoDTO;
import com.platform.OneSkill.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.core.io.Resource;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/videos")
public class VideoController {

    private final VideoService videoService;
    @PostMapping("/upload")
    public ResponseEntity<String> uploadVideo(@ModelAttribute VideoDTO videoDTO){
        return videoService.uploadVideo(videoDTO)
                ? ResponseEntity.status(HttpStatus.CREATED).body("Video has been uploaded.")
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Video hasn't been uploaded.");
    }

    @GetMapping(value = "/stream", produces = "video/mp4")
    public Mono<Resource> streamVideo(@RequestParam String username,
                                      @RequestParam String title) {
        return videoService.getVideoByUsernameAndTitle(username, title);
    }
}
