package com.bootcamp.reactive.retoblog.services.impl;

import com.bootcamp.reactive.retoblog.core.exception.AuthorBadRequestException;
import com.bootcamp.reactive.retoblog.core.exception.BlogBadRequestException;
import com.bootcamp.reactive.retoblog.entities.Blog;
import com.bootcamp.reactive.retoblog.repositories.AuthorRepository;
import com.bootcamp.reactive.retoblog.repositories.BlogRepository;
import com.bootcamp.reactive.retoblog.services.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;

@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Override
    public Mono<Blog> findById(String id) {
        return  this.blogRepository.findById(id);
    }

    @Override
    public Flux<Blog> findAll() {
        return blogRepository.findAll();
    }

    @Override
    public Mono<Blog> save(Blog blog) {
        return this.authorRepository.findById(blog.getAuthorId())
                .flatMap(author -> {
                    Period period = Period.between(
                            author.getBirthDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                            LocalDate.now()
                    );
                    return period.getYears() < 18 ? Mono.error(new AuthorBadRequestException("Solo pueden tener blogs los autores mayores de 18 años"))
                            : this.blogRepository.countByAuthorId(blog.getAuthorId());
                })
                .flatMap(blogQuantity -> blogQuantity < 3 ? this.blogRepository.save(blog)
                        : Mono.error(new BlogBadRequestException("Un autor puede tener máximo 03 blogs")));
    }

    @Override
    public Mono<Void> delete(String id) {
        return this.blogRepository.findById(id)
                .doOnNext(b->{
                    System.out.println("doOnNext b = " + b);
                })
                .flatMap(blog-> this.blogRepository.delete(blog));

    }

    @Override
    public Mono<Void> deleteByAuthorId(String authorId) {
        return this.blogRepository.findByAuthorId(authorId)
                .flatMap(blog-> this.blogRepository.deleteByAuthorId(blog.getAuthorId()))
                .then(Mono.empty());
    }

    @Override
    public Flux<Blog> findByAuthorId(String authorId) {
        return this.blogRepository.findByAuthorId(authorId);
    }
}
