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

import static com.platform.OneSkill.util.TimeUtil.getCurrentZonedDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class VideoServiceImpl implements VideoService {

    private final VideoRepository videoRepository;
    @Override
    @Transactional
    public Mono<Boolean> uploadVideo(VideoDTO dto) {
        Mono<Video> videoMono = Mono.fromSupplier(() -> {
            Video video = new Video();
            video.setUsername(dto.username());
            video.setTitle(dto.title());
            video.setDescription(dto.description());

            try {
                video.setVideoData(dto.videoFile().getBytes());
                video.setThumbnailData(dto.thumbnailFile().getBytes());
            } catch (Exception e) {
                System.err.println("Error while reading file: " + e.getMessage());
                throw new RuntimeException(e);
            }

            video.setUploadDate(getCurrentZonedDateTime());
            video.setStatus("active");
            video.setLikes(0);
            video.setDislikes(0);
            video.setSharedCount(0);
            video.setViews(0);

            return video;
        });

        Mono<Video> savedVideoMono = videoMono.flatMap(videoRepository::save);

        Mono<Boolean> resultMono = savedVideoMono.map(savedVideo -> true);

        return resultMono.onErrorReturn(false);
    }

    @Override
    public Mono<Resource> getVideoByUsernameAndTitle(String username, String title) {

        return videoRepository.findByUsernameAndTitle(username, title)
                .map(video -> new ByteArrayResource(video.getVideoData()));

    }
}
