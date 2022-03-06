package com.bootcamp.reactive.retoblog.handlers;

import com.bootcamp.reactive.retoblog.entities.Post;
import com.bootcamp.reactive.retoblog.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
public class PostHandler {

    @Autowired
    private PostService postService;

    public Mono<ServerResponse> save(ServerRequest request) {
        return request.bodyToMono(Post.class)
                .flatMap(post -> this.postService.save(post))
                .flatMap(post -> ServerResponse.ok().body(Mono.just(post), Post.class));
    }

    public Mono<ServerResponse> findAll(ServerRequest serverRequest) {
        return ok().contentType(APPLICATION_JSON)
                .body(postService.findAll(), Post.class);
    }
}
