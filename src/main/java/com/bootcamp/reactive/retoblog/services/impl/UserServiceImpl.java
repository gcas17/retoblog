package com.bootcamp.reactive.retoblog.services.impl;

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
        return this.userRepository.save(user);
    }

    @Override
    public Flux<User> findAll() {
        return this.userRepository.findAll();
    }
}
