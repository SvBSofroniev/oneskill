package com.platform.OneSkill.service.impl;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.platform.OneSkill.dto.VideoDTO;
import com.platform.OneSkill.dto.VideoResponseDTO;
import com.platform.OneSkill.persistance.models.Video;
import com.platform.OneSkill.persistance.repository.VideoRepository;
import com.platform.OneSkill.service.VideoService;
import com.platform.OneSkill.util.TimeUtil;
import com.platform.OneSkill.util.VideoStatus;
import java.io.IOException;
import java.io.InputStream;
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
    private final GridFsTemplate gridFsTemplate;

    private final GridFsOperations operations;

    @Override
    @Transactional
    public boolean uploadVideo(VideoDTO dto) throws IOException {


        Video video = new Video();
        video.setUsername(dto.username());
        video.setTitle(dto.title());
        video.setDescription(dto.description());
        video.setUploadDate(TimeUtil.getCurrentZonedDateTime());
        video.setStatus(VideoStatus.ACTIVE.getValue());
        video.setViews(0);
        video.setLikes(0);
        video.setDislikes(0);
        video.setSharedCount(0);
        video = videoRepository.save(video);

        ObjectId videoId = saveFile(dto, dto.videoFile());
        ObjectId thumbnailId = saveFile(dto, dto.thumbnailFile());
        video.setVideoId(videoId);
        video.setThumbnailId(thumbnailId);
        videoRepository.save(video);
        return true;
    }

    @Override
    public VideoResponseDTO getVideo(String id) throws IOException {
        GridFSFile file = gridFsTemplate.findOne(new Query(Criteria.where("_id").is(id)));
        if (file != null && file.getMetadata() != null) {
            InputStream video = operations.getResource(file).getInputStream();
            return new VideoResponseDTO(video);
        } else return null;
    }

    private ObjectId saveFile(VideoDTO dto, MultipartFile file) throws IOException {
        DBObject metaData = new BasicDBObject();
        metaData.put(USERNAME, dto.username());
        metaData.put(DESCRIPTION, dto.description());
        metaData.put(TITLE, dto.title());
        return gridFsTemplate.store(
                file.getInputStream(),
                file.getName(),
                file.getContentType(),
                metaData);
    }
}
