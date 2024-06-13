package com.platform.OneSkill.service;

import com.platform.OneSkill.dto.VideoDTO;
import com.platform.OneSkill.persistance.models.Video;
import org.springframework.core.io.Resource;
import reactor.core.publisher.Mono;

import java.util.Optional;

public interface VideoService {
    Mono<Boolean> uploadVideo(VideoDTO videoDTO);

    Mono<Resource> getVideoByUsernameAndTitle(String username, String title);
}
