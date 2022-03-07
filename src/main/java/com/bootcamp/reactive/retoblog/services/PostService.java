package com.bootcamp.reactive.retoblog.services;

import com.bootcamp.reactive.retoblog.entities.Post;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PostService {

    Mono<Post> save(Post post);

    Flux<Post> findAll();

    Mono<Void> deleteByAuthorId(String authorId);

    Flux<Post> findByBlogId(String blogId);

}
