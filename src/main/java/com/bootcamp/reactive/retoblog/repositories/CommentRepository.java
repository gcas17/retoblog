package com.bootcamp.reactive.retoblog.repositories;

import com.bootcamp.reactive.retoblog.entities.Comment;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface CommentRepository extends ReactiveMongoRepository<Comment, String> {
    Flux<Comment> findByPostId(String postId);
}
