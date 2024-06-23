package com.platform.OneSkill.persistance.repository;

import com.platform.OneSkill.persistance.models.EnrolledVideo;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnrolledVideoRepository extends MongoRepository<EnrolledVideo, String> {

    Optional<EnrolledVideo> findByUsernameAndVideoId(String username, ObjectId videoId);

    List<EnrolledVideo> findAllByUsername(String username);
}
