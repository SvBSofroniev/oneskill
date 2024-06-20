package com.platform.OneSkill.persistance.repository;

import com.platform.OneSkill.persistance.models.Video;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoRepository extends MongoRepository<Video, String> {
}
