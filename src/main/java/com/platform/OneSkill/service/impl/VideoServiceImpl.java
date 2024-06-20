package com.platform.OneSkill.service.impl;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.platform.OneSkill.dto.VideoDTO;
import com.platform.OneSkill.dto.VideoResponseDTO;
import com.platform.OneSkill.service.VideoService;
import java.io.IOException;
import java.io.InputStream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;



@Slf4j
@Service
@RequiredArgsConstructor
public class VideoServiceImpl implements VideoService {

    @Autowired
    private GridFsTemplate gridFsTemplate;

    @Autowired
    private GridFsOperations operations;

    @Override
    @Transactional
    public boolean uploadVideo(VideoDTO dto) throws IOException {

        MultipartFile file = dto.videoFile();
        DBObject metaData = new BasicDBObject();
        metaData.put("username", dto.username());
        metaData.put("description", dto.description());
        metaData.put("title", dto.title());

        ObjectId id = gridFsTemplate.store(file.getInputStream(), file.getName(), file.getContentType(), metaData);
        if (id == null){
            return false;
        }else {
            return true;
        }
    }

    public VideoResponseDTO getVideo(String id) throws IOException {
        GridFSFile file = gridFsTemplate.findOne(new Query(Criteria.where("_id").is(id)));
        String resultTitle = file.getMetadata().getString("title");
        String description = file.getMetadata().getString("description");
        InputStream video = operations.getResource(file).getInputStream();
        return new VideoResponseDTO(id, description, video);
    }
}
