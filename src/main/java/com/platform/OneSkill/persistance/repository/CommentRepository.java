package com.platform.OneSkill.persistance.repository;

import com.platform.OneSkill.persistance.models.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends MongoRepository<Comment, String> {

    List<Comment> findAllByVideoId(String videoId);
}
