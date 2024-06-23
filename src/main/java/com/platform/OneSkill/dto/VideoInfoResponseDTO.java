package com.platform.OneSkill.dto;


public record VideoInfoResponseDTO(
        String videoId,
        String title,
        String description,
        String status,
        String uploadDate,
        String thumbnailBase64,
        int likes,
        int dislikes,
        int views
) {
}
