package com.platform.OneSkill.dto;

import org.springframework.web.multipart.MultipartFile;

public record VideoUploadDTO(
    String title,
    String description,
    MultipartFile videoFile,
    MultipartFile thumbnailFile
){
}
