package com.platform.OneSkill.service;

import com.platform.OneSkill.dto.VideoDTO;
import com.platform.OneSkill.persistance.models.Video;

import java.util.Optional;

public interface VideoService {
    boolean uploadVideo(VideoDTO videoDTO);

    Optional<Video> getVideoByUsernameAndTitle(String username, String title);
}
