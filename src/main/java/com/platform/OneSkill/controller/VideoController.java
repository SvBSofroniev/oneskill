package com.platform.OneSkill.controller;

import com.platform.OneSkill.dto.VideoIdDTO;
import com.platform.OneSkill.dto.VideoInfoResponseDTO;
import com.platform.OneSkill.dto.VideoUploadDTO;
import com.platform.OneSkill.dto.VideoResponseDTO;
import com.platform.OneSkill.service.VideoService;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "http://localhost:4200")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/videos")
public class VideoController {

    private final VideoService videoService;

    @PostMapping("/upload")
    public boolean uploadVideo(@ModelAttribute VideoUploadDTO videoUploadDTO, Principal principal) throws IOException {
        return videoService.uploadVideo(principal.getName(), videoUploadDTO);
    }

    @GetMapping(value = "/stream/{id}")
    public void streamVideo(@PathVariable String id,  HttpServletResponse response) throws IOException {
        VideoResponseDTO foundVideo = videoService.getVideo(id);
        response.setContentType("video/mp4");
        response.setHeader("Content-Disposition", "inline; filename=\"video.mp4\"");
        response.setHeader("Accept-Ranges", "bytes");

        try (InputStream inputStream = foundVideo.stream()) {
            FileCopyUtils.copy(inputStream, response.getOutputStream());
        } catch (IOException e) {
            log.error("Error streaming video", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public List<VideoInfoResponseDTO> getVideosInfoData(){
        return videoService.getVideosInfoData();
    }

    @GetMapping("{id}")
    public VideoInfoResponseDTO getVideoInfoData(String id) throws IOException {
        return videoService.getVideoInfoData(id);
    }


    @GetMapping("/hello")
    public String hello(){
        return "Hello";
    }

    @GetMapping("/enrolled")
    public List<VideoIdDTO> getEnrolledVideos(Principal principal){
        return videoService.getEnrolledVideos(principal.getName());
    }

    @PostMapping(value = "/enroll")
    public boolean enrollVideo(@RequestBody String id, Principal principal){
        return videoService.enrollToVideo(principal.getName(), id);
    }
}
