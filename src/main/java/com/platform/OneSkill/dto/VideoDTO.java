package com.platform.OneSkill.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

public record VideoDTO (
    String username,
    String title,
    String description,
    MultipartFile videoFile,
    MultipartFile thumbnailFile
){
}
