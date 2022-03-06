package com.bootcamp.reactive.retoblog.services.impl;

import com.bootcamp.reactive.retoblog.entities.Post;
import com.bootcamp.reactive.retoblog.repositories.PostRepository;
import com.bootcamp.reactive.retoblog.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Override
    public Mono<Post> save(Post post) {
        return this.postRepository.save(post);
    }

    @Override
    public Flux<Post> findAll() {
        return this.postRepository.findAll();
    }
}
