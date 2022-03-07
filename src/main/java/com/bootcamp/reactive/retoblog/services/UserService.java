package com.bootcamp.reactive.retoblog.services;

import com.bootcamp.reactive.retoblog.entities.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {
    Mono<User> save(User user);
    Flux<User> findAll();
    Mono<User> auth(String login, String password);
}
