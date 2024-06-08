package com.platform.OneSkill.persistance.repository;

import com.platform.OneSkill.persistance.models.Rating;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingRepository extends MongoRepository<Rating, String> {
}
