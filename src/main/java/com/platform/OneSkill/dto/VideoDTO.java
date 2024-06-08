package com.platform.OneSkill.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class VideoDTO {
    private String username;
    private String title;
    private String description;
    private MultipartFile videoFile;
    private MultipartFile thumbnailFile;
}
