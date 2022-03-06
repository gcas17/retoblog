package com.bootcamp.reactive.retoblog.services;

import com.bootcamp.reactive.retoblog.entities.Comment;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CommentService {
    Mono<Comment> save(Comment comment);
    Flux<Comment> findAll();
}
