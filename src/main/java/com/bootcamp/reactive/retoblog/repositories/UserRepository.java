package com.bootcamp.reactive.retoblog.repositories;

import com.bootcamp.reactive.retoblog.entities.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends ReactiveMongoRepository<User, String> {

    Mono<User> findByLogin(String login);

    Mono<Boolean> existsByAuthorId(String authorId);

}
