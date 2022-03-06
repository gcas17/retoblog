package com.bootcamp.reactive.retoblog.services.impl;

import com.bootcamp.reactive.retoblog.entities.Comment;
import com.bootcamp.reactive.retoblog.repositories.CommentRepository;
import com.bootcamp.reactive.retoblog.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public Mono<Comment> save(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public Flux<Comment> findAll() {
        return commentRepository.findAll();
    }
}
