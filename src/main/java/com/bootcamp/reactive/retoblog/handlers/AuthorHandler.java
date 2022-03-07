package com.bootcamp.reactive.retoblog.handlers;

import com.bootcamp.reactive.retoblog.entities.Author;
import com.bootcamp.reactive.retoblog.services.AuthorService;
import com.bootcamp.reactive.retoblog.services.BlogService;
import com.bootcamp.reactive.retoblog.services.CommentService;
import com.bootcamp.reactive.retoblog.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Component
public class AuthorHandler {

    @Autowired
    private AuthorService authorService;

    @Autowired
    private BlogService blogService;

    @Autowired
    private PostService postService;

    @Autowired
    private CommentService commentService;

    public Mono<ServerResponse> findAll(ServerRequest request){
        return ServerResponse.ok()
                .contentType(APPLICATION_JSON)
                .body(authorService.findAll(), Author.class);
    }

    public Mono<ServerResponse> findById(ServerRequest request){
        String id = request.pathVariable("id");
        return this.authorService.findById(id)
                .flatMap(a-> ServerResponse.ok().body(Mono.just(a), Author.class))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> findByEmail(ServerRequest request){
        String email = request.queryParam("email").get();
        return ServerResponse.ok()
                .contentType(APPLICATION_JSON)
                .body(authorService.findByEmail(email), Author.class);
    }

    public Mono<ServerResponse> save(ServerRequest request){
        return request.bodyToMono(Author.class)
                .flatMap(author-> this.authorService.saveWithValidation(author))
                .flatMap(a-> ServerResponse
                        .ok()
                        .contentType(APPLICATION_JSON)
                        .body(Mono.just(a), Author.class));
    }

    public Mono<ServerResponse> delete(ServerRequest serverRequest) {
        String authorId = serverRequest.pathVariable("id");
        return this.authorService.delete(authorId)
                .then(this.commentService.deleteByAuthorId(authorId))
                .then(this.postService.deleteByAuthorId(authorId))
                .then(this.blogService.deleteByAuthorId(authorId))
                .then(ServerResponse.ok().build());
    }
}
