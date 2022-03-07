package com.bootcamp.reactive.retoblog.services.impl;

import com.bootcamp.reactive.retoblog.core.exception.AuthorNotFoundException;
import com.bootcamp.reactive.retoblog.core.exception.CommentBadRequestException;
import com.bootcamp.reactive.retoblog.core.exception.ReactionNotFoundException;
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
        return this.reactionRepository.existsByPostId(reaction.getPostId())
                .flatMap(exists -> exists
                        ? Mono.error(new CommentBadRequestException("Un usuario solo puede tener una reacción para cada post."))
                        : this.reactionRepository.save(reaction)
                );
    }

    @Override
    public Flux<Reaction> findAll() {
        return this.reactionRepository.findAll();
    }

    @Override
    public Mono<Void> delete(String id) {
        return this.reactionRepository.findById(id)
                .switchIfEmpty(Mono.error(new ReactionNotFoundException("Reaccion no encontrada")))
                .flatMap(reaction-> this.reactionRepository.delete(reaction));
    }
}
