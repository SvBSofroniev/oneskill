package com.platform.OneSkill.persistance.repository.reactive;

import com.platform.OneSkill.persistance.models.Video;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface VideoRepository extends ReactiveMongoRepository<Video, String> {
    Mono<Video> findByUsernameAndTitle(String username, String title);
}
