package com.platform.OneSkill.persistance.repository;

import com.platform.OneSkill.persistance.models.LikeDislike;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeDislikeRepository extends MongoRepository<LikeDislike, String> {
}