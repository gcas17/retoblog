package com.bootcamp.reactive.retoblog.services;

import com.bootcamp.reactive.retoblog.entities.Blog;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BlogService {
    Mono<Blog> findById(String id);
    Flux<Blog> findAll();
    Mono<Blog> save(Blog blog);
    Mono<Void> delete(String id);
}
