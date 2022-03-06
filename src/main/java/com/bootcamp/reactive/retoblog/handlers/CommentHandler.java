package com.bootcamp.reactive.retoblog.handlers;

import com.bootcamp.reactive.retoblog.entities.Comment;
import com.bootcamp.reactive.retoblog.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
public class CommentHandler {

    @Autowired
    private CommentService commentService;

    public Mono<ServerResponse> save(ServerRequest request) {
        return request.bodyToMono(Comment.class)
                .flatMap(comment -> this.commentService.save(comment))
                .flatMap(comment -> ServerResponse.ok().body(Mono.just(comment), Comment.class));
    }

    public Mono<ServerResponse> findAll(ServerRequest serverRequest) {
        return ok().contentType(APPLICATION_JSON)
                .body(commentService.findAll(), Comment.class);
    }

}
