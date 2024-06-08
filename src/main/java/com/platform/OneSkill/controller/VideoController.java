package com.platform.OneSkill.controller;

import com.platform.OneSkill.dto.VideoDTO;
import com.platform.OneSkill.persistance.models.Video;
import com.platform.OneSkill.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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

    @GetMapping("/stream")
    public ResponseEntity<byte[]> streamVideo(@RequestParam String username,
                                              @RequestParam String title) {
        Optional<Video> videoOptional = videoService.getVideoByUsernameAndTitle(username, title);

        if (videoOptional.isPresent()) {
            Video video = videoOptional.get();
            byte[] videoData = video.getVideoData();

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "video/mp4");
            headers.add("Accept-Ranges", "bytes");

            return new ResponseEntity<>(videoData, headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
