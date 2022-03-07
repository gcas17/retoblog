package com.bootcamp.reactive.retoblog.services.impl;

import com.bootcamp.reactive.retoblog.core.exception.AuthorNotFoundException;
import com.bootcamp.reactive.retoblog.core.exception.CommentBadRequestException;
import com.bootcamp.reactive.retoblog.entities.Comment;
import com.bootcamp.reactive.retoblog.repositories.BlogRepository;
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

    @Autowired
    private BlogRepository blogRepository;

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

    @Override
    public Mono<Void> deleteByAuthorId(String authorId) {
        return this.blogRepository.findByAuthorId(authorId)
                .flatMap(blog -> this.postRepository.findByBlogId(blog.getId()))
                .flatMap(post -> this.commentRepository.findByPostId(post.getId()))
                .flatMap(comment -> this.commentRepository.deleteById(comment.getId()))
                .then(Mono.empty());
    }

    @Override
    public Flux<Comment> findByPostId(String postId) {
        return commentRepository.findByPostId(postId);
    }
}
