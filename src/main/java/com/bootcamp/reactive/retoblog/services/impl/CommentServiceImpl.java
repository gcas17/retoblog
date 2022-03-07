package com.bootcamp.reactive.retoblog.services.impl;

import com.bootcamp.reactive.retoblog.core.exception.CommentBadRequestException;
import com.bootcamp.reactive.retoblog.entities.Comment;
import com.bootcamp.reactive.retoblog.repositories.CommentRepository;
import com.bootcamp.reactive.retoblog.repositories.PostRepository;
import com.bootcamp.reactive.retoblog.services.CommentService;
import com.bootcamp.reactive.retoblog.util.enums.PostStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Override
    public Mono<Comment> save(Comment comment) {
        return this.postRepository.findById(comment.getPostId())
                .flatMap(post -> post.getStatus().toLowerCase().equals(PostStatus.DRAFT.getDescription().toLowerCase())
                        ? Mono.error(new CommentBadRequestException("Solo se pueden registrar comentarios en post en estado publicado."))
                        : commentRepository.save(comment));
    }

    @Override
    public Flux<Comment> findAll() {
        return commentRepository.findAll();
    }
}
