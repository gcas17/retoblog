package com.bootcamp.reactive.retoblog.handlers;

import com.bootcamp.reactive.retoblog.entities.Reaction;
import com.bootcamp.reactive.retoblog.services.ReactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.ServerResponse.badRequest;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
public class ReactionHandler {

    @Autowired
    private ReactionService reactionService;

    public Mono<ServerResponse> save(ServerRequest request) {
        return request.bodyToMono(Reaction.class)
                .flatMap(reaction -> reactionService.save(reaction))
                .flatMap(reaction -> ServerResponse.ok().body(Mono.just(reaction), Reaction.class));
    }

    public Mono<ServerResponse> findAll(ServerRequest serverRequest) {
        return ok().contentType(APPLICATION_JSON)
                .body(reactionService.findAll(), Reaction.class);
    }

    public Mono<ServerResponse> delete(ServerRequest serverRequest) {
        String reactionId = serverRequest.pathVariable("id");
        if (!isValidId(reactionId)) return badRequest().build();

        return this.reactionService.delete(serverRequest.pathVariable("id"))
                .then(ServerResponse.noContent().build());
    }

    private boolean isValidId(String id) {
        return id != null && id.length() > 5;
    }
}
