package com.platform.OneSkill.service.impl;

import com.platform.OneSkill.dto.VideoDTO;
import com.platform.OneSkill.persistance.models.Video;
import com.platform.OneSkill.persistance.repository.reactive.VideoRepository;
import com.platform.OneSkill.service.VideoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
public class VideoServiceImpl implements VideoService {

    private final VideoRepository videoRepository;
    @Transactional
    @Override
    public boolean uploadVideo(VideoDTO dto) {

        try {
            Video video = new Video();
            video.setUsername(dto.getUsername());
            video.setTitle(dto.getTitle());
            video.setDescription(dto.getDescription());
            video.setVideoData(dto.getVideoFile().getBytes());
            video.setThumbnailData(dto.getThumbnailFile().getBytes());
            video.setUploadDate(Date.from(Instant.now()));
            video.setStatus("active");
            video.setLikes(0);
            video.setDislikes(0);
            video.setSharedCount(0);
            video.setViews(0);
            videoRepository.save(video);
        }catch (Exception e){
            log.error(e.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public Mono<Resource> getVideoByUsernameAndTitle(String username, String title) {
        return videoRepository.findByUsernameAndTitle(username, title)
                .map(video -> new ByteArrayResource(video.getVideoData()));
    }
}
