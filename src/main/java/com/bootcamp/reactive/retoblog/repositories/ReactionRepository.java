package com.bootcamp.reactive.retoblog.repositories;

import com.bootcamp.reactive.retoblog.entities.Reaction;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface ReactionRepository extends ReactiveMongoRepository<Reaction, String> {
    Mono<Boolean> existsByPostId(String postId);
}
