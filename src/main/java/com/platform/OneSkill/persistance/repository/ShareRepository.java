package com.platform.OneSkill.persistance.repository;

import com.platform.OneSkill.persistance.models.Share;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShareRepository extends MongoRepository<Share, String> {
}
