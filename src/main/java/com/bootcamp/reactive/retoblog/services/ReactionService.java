package com.bootcamp.reactive.retoblog.services;

import com.bootcamp.reactive.retoblog.entities.Reaction;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ReactionService {
    Mono<Reaction> save(Reaction reaction);
    Flux<Reaction> findAll();
    Mono<Void> delete(String id);
}
