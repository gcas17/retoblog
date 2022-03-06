package com.bootcamp.reactive.retoblog.services.impl;

import com.bootcamp.reactive.retoblog.entities.Reaction;
import com.bootcamp.reactive.retoblog.repositories.ReactionRepository;
import com.bootcamp.reactive.retoblog.services.ReactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ReactionServiceImpl implements ReactionService {

    @Autowired
    private ReactionRepository reactionRepository;

    @Override
    public Mono<Reaction> save(Reaction reaction) {
        return reactionRepository.save(reaction);
    }

    @Override
    public Flux<Reaction> findAll() {
        return reactionRepository.findAll();
    }
}
