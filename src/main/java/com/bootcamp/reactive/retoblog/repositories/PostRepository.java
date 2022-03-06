package com.bootcamp.reactive.retoblog.repositories;

import com.bootcamp.reactive.retoblog.entities.Post;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends ReactiveMongoRepository<Post, String> {
}
