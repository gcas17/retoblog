package com.bootcamp.reactive.retoblog.repositories;

import com.bootcamp.reactive.retoblog.entities.Blog;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Repository
public interface BlogRepository extends ReactiveMongoRepository<Blog, String> {

    Mono<Long> countByAuthorId(String authorId);

    Flux<Blog> findByAuthorId(String authorId);

    Flux<String> deleteByAuthorId(String authorId);

}