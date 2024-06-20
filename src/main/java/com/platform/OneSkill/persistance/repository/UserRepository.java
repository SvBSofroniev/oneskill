package com.platform.OneSkill.persistance.repository;

import com.platform.OneSkill.persistance.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    boolean existsByUsernameAndEmail(String username, String email);

    Optional<User> findByUsername(String username);

}
