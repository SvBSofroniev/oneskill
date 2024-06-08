package com.platform.OneSkill.persistance.repository;

import com.platform.OneSkill.persistance.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
}
