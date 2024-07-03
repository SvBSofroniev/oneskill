package com.platform.OneSkill.persistance.repository;

import com.platform.OneSkill.persistance.models.LikeDislike;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeDislikeRepository extends MongoRepository<LikeDislike, String> {

    @Query("{ 'videoId': ?0, 'username': ?1}")
    LikeDislike findByVideoIdAndUsername(String videoId, String username);
}
