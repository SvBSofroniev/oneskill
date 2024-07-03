package com.platform.OneSkill.service.impl;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.platform.OneSkill.dto.*;
import com.platform.OneSkill.persistance.models.EnrolledVideo;
import com.platform.OneSkill.persistance.models.LikeDislike;
import com.platform.OneSkill.persistance.models.Video;
import com.platform.OneSkill.persistance.repository.EnrolledVideoRepository;
import com.platform.OneSkill.persistance.repository.LikeDislikeRepository;
import com.platform.OneSkill.persistance.repository.VideoRepository;
import com.platform.OneSkill.service.VideoService;
import com.platform.OneSkill.util.InteractionEnum;
import com.platform.OneSkill.util.TimeUtil;
import com.platform.OneSkill.util.VideoStatus;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.function.Function;

import com.platform.OneSkill.util.mapper.VideoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


@Slf4j
@Service
@RequiredArgsConstructor
public class VideoServiceImpl implements VideoService {

    public static final String DESCRIPTION = "description";
    public static final String TITLE = "title";
    public static final String USERNAME = "username";
    private final VideoRepository videoRepository;
    private final LikeDislikeRepository likeDislikeRepository;
    private final EnrolledVideoRepository enrolledVideoRepository;
    private final GridFsTemplate gridFsTemplate;
    private final GridFsOperations operations;

    @Override
    @Transactional
    public boolean uploadVideo(String username, VideoUploadDTO dto) throws IOException {
        Video video = new Video();
        video.setUsername(username);
        video.setTitle(dto.title());
        video.setDescription(dto.description());
        video.setUploadDate(TimeUtil.getCurrentZonedDateTime());
        video.setStatus(VideoStatus.ACTIVE.getValue());
        video.setViews(0);
        video.setLikes(0);
        video.setDislikes(0);
        video.setSharedCount(0);
        video = videoRepository.save(video);

        ObjectId videoId = saveFile(username, dto, dto.videoFile());
        ObjectId thumbnailId = saveFile(username, dto, dto.thumbnailFile());
        video.setVideoId(videoId);
        video.setThumbnailId(thumbnailId);
        videoRepository.save(video);
        return true;
    }

    @Override
    public VideoResponseDTO getVideo(String id) throws IOException {
        return getMedia(id, VideoResponseDTO::new);
    }

    public ImageDTO getImage(String id) throws IOException {
        return getMedia(id, ImageDTO::new);
    }

    public <T> T getMedia(String id, Function<InputStream, T> dtoConstructor) throws IOException {
        GridFSFile file = gridFsTemplate.findOne(new Query(Criteria.where("_id").is(id)));
        if (file != null && file.getMetadata() != null) {
            InputStream inputStream = operations.getResource(file).getInputStream();
            return dtoConstructor.apply(inputStream);
        } else {
            return null;
        }
    }

    @Transactional
    @Override
    public List<VideoInfoResponseDTO> getVideosInfoData() {
        List<Video> resultList = videoRepository.findAll();
        Map<String, ImageDTO> videoThumbnailMap = new HashMap<>();

        if (!resultList.isEmpty()) {
            resultList.forEach(video -> {
                try {
                    ImageDTO thumbnail = getImage(video.getThumbnailId().toString());
                    log.info("Thumbnail:" + thumbnail);
                    videoThumbnailMap.put(video.getTitle(), thumbnail);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        return VideoMapper.mapModelListToResponseInfoDTOList(resultList, videoThumbnailMap);
    }

    @Override
    public List<VideoIdDTO> getEnrolledVideos(String username) {
        List<EnrolledVideo> foundVideos = enrolledVideoRepository.findAllByUsername(username);
        if (foundVideos != null && !foundVideos.isEmpty()){
            List<VideoIdDTO> mappedReturn = new ArrayList<>();
            foundVideos.forEach(foundVideo ->{
                mappedReturn.add(new VideoIdDTO(foundVideo.getVideoId()));
            });
            return mappedReturn;
        }

        return List.of();
    }

    @Transactional
    @Override
    public Boolean enrollToVideo(String username, String videoId) {

        Optional<EnrolledVideo> foundEnrollment = enrolledVideoRepository.findByUsernameAndVideoId(username, new ObjectId(videoId));
        if (foundEnrollment.isEmpty()) {
            EnrolledVideo enrolledVideo = new EnrolledVideo();
            enrolledVideo.setVideoId(videoId);
            enrolledVideo.setUsername(username);
            enrolledVideoRepository.save(enrolledVideo);
            return true;
        }
        return false;
    }

    @Override
    public VideoInfoResponseDTO getVideoInfoData(String id) {
        Optional<Video> foundVideo = videoRepository.findByVideoId(new ObjectId(id));

        Video video = foundVideo.orElseThrow();

        return new VideoInfoResponseDTO(
                video.getId(),
                video.getTitle(),
                video.getDescription(),
                video.getStatus(),
                TimeUtil.getFormattedTime(video.getUploadDate()),
                null,
                video.getLikes(),
                video.getDislikes(),
                video.getViews()
        );
    }

    @Override
    public void registerView(String id) {
        Optional<Video> foundVideo = videoRepository.findByVideoId(new ObjectId(id));
        foundVideo.ifPresent(video -> {
            video.setViews(video.getViews() + 1);
            videoRepository.save(video);
        });
    }

    @Transactional
    @Override
    public void interactWithVideo(String id, InteractDTO interactDTO) {
        assert interactDTO != null;
        assert interactDTO.action() != null;

        InteractionEnum requestedAction = InteractionEnum.fromString(interactDTO.action());
        assert requestedAction != null;

        LikeDislike foundResult = likeDislikeRepository.findByVideoIdAndUsername(id, interactDTO.username());

        if (foundResult != null) {
            if (requestedAction.getValue().equals(foundResult.getType())) {
                updateInteraction(foundResult, InteractionEnum.NONE, id, requestedAction);
            } else {
                updateInteraction(foundResult, requestedAction, id, requestedAction);
            }
        } else {
            createNewInteraction(id, interactDTO, requestedAction);
        }
    }

    private void updateInteraction(LikeDislike foundResult, InteractionEnum newType, String videoId, InteractionEnum requestedAction) {
        if (!newType.getValue().equals(foundResult.getType())) {
            Optional<Video> foundVideo = videoRepository.findById(videoId);
            foundVideo.ifPresent(video -> {
                adjustCounts(video, foundResult.getType(), newType.getValue());
                videoRepository.save(video);
            });
            foundResult.setType(newType.getValue());
            likeDislikeRepository.save(foundResult);
        }
    }

    private void createNewInteraction(String videoId, InteractDTO interactDTO, InteractionEnum requestedAction) {
        LikeDislike newInteraction = new LikeDislike();
        newInteraction.setVideoId(videoId);
        newInteraction.setUsername(interactDTO.username());
        newInteraction.setType(requestedAction.getValue());
        likeDislikeRepository.save(newInteraction);

        Optional<Video> foundVideo = videoRepository.findById(videoId);
        foundVideo.ifPresent(video -> {
            adjustCounts(video, InteractionEnum.NONE.getValue(), requestedAction.getValue());
            videoRepository.save(video);
        });
    }

    private void adjustCounts(Video video, String oldType, String newType) {
        if (InteractionEnum.LIKE.getValue().equals(oldType)) {
            video.setLikes(video.getLikes() - 1);
        } else if (InteractionEnum.DISLIKE.getValue().equals(oldType)) {
            video.setDislikes(video.getDislikes() - 1);
        }

        if (InteractionEnum.LIKE.getValue().equals(newType)) {
            video.setLikes(video.getLikes() + 1);
        } else if (InteractionEnum.DISLIKE.getValue().equals(newType)) {
            video.setDislikes(video.getDislikes() + 1);
        }
    }


    private ObjectId saveFile(String username, VideoUploadDTO dto, MultipartFile file) throws IOException {
        DBObject metaData = new BasicDBObject();
        metaData.put(USERNAME, username);
        metaData.put(DESCRIPTION, dto.description());
        metaData.put(TITLE, dto.title());
        return gridFsTemplate.store(
                file.getInputStream(),
                file.getName(),
                file.getContentType(),
                metaData);
    }
}
