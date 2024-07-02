package com.platform.OneSkill.persistance.repository;

import com.platform.OneSkill.persistance.models.Video;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VideoRepository extends MongoRepository<Video, String> {

    Optional<Video> findByUsernameAndVideoId(String username, String videoId);
    Optional<Video> findByVideoId(ObjectId videoId);

}
