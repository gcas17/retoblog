package com.bootcamp.reactive.retoblog.services.impl;

import com.bootcamp.reactive.retoblog.core.exception.AuthorBadRequestException;
import com.bootcamp.reactive.retoblog.core.exception.AuthorNotFoundException;
import com.bootcamp.reactive.retoblog.entities.Author;
import com.bootcamp.reactive.retoblog.entities.Blog;
import com.bootcamp.reactive.retoblog.entities.Post;
import com.bootcamp.reactive.retoblog.repositories.AuthorRepository;
import com.bootcamp.reactive.retoblog.repositories.BlogRepository;
import com.bootcamp.reactive.retoblog.repositories.CommentRepository;
import com.bootcamp.reactive.retoblog.repositories.PostRepository;
import com.bootcamp.reactive.retoblog.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AuthorServiceImpl implements AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public Mono<Author> findById(String id) {
        return this.authorRepository.findById(id);
    }

    @Override
    public Mono<Boolean> existsByEmail(String email) {
        return authorRepository.existsByEmail(email);
    }

    @Override
    public Flux<Author> findByEmail(String email) {
        return this.authorRepository.findByEmail(email);
    }

    @Override
    public Flux<Author> findByName(String name) {
        return this.authorRepository.findByName(name);
    }

    @Override
    public Flux<Author> findAll() {
        return this.authorRepository.findAll();
    }

    @Override
    public Mono<Author> save(Author author) {
        return this.authorRepository.save(author);
    }

    @Override
    public Mono<Author> saveWithValidation(Author author) {
        return this.authorRepository.existsByEmail(author.getEmail())
                .flatMap(exists-> {
                    return !exists ? this.authorRepository.save(author): Mono.error(new AuthorBadRequestException("El autor ya existe"));
                });
    }

    @Override
    public Mono<Void> delete(String id) {
        return this.authorRepository.findById(id)
            .switchIfEmpty(Mono.error(new AuthorNotFoundException("Author no encontrado")))
            .flatMap(author-> this.authorRepository.delete(author));
    }

}
