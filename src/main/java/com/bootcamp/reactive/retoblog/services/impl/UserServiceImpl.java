package com.bootcamp.reactive.retoblog.services.impl;

import com.bootcamp.reactive.retoblog.core.exception.UserBadRequestException;
import com.bootcamp.reactive.retoblog.core.exception.UserNotFoundException;
import com.bootcamp.reactive.retoblog.core.exception.UserPasswordException;
import com.bootcamp.reactive.retoblog.entities.User;
import com.bootcamp.reactive.retoblog.repositories.UserRepository;
import com.bootcamp.reactive.retoblog.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Mono<User> save(User user) {
        return this.userRepository.existsByAuthorId(user.getAuthorId())
                .flatMap(exists -> {
                    return !exists ? this.userRepository.save(user) : Mono.error(new UserBadRequestException("Un autor debe tener solo un usuario"));
                });
    }

    @Override
    public Flux<User> findAll() {
        return this.userRepository.findAll();
    }

    @Override
    public Mono<User> auth(String login, String password) {
        return this.userRepository.findByLogin(login)
                .switchIfEmpty(Mono.error(new UserNotFoundException("Usuario no encontrado")))
                .flatMap(user -> {
                    return user.getPassword().equals(password) ? Mono.just(user) : Mono.error(new UserPasswordException("Password incorrecto"));
                });
    }
}
