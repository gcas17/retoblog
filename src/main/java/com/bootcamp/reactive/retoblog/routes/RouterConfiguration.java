package com.bootcamp.reactive.retoblog.routes;

import com.bootcamp.reactive.retoblog.handlers.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;

@Configuration
public class RouterConfiguration {

    @Bean
    public RouterFunction<ServerResponse> blogRoutes(BlogHandler blogHandler) {
        return RouterFunctions.nest(RequestPredicates.path("/blogs"),
                RouterFunctions
                        .route(GET(""), blogHandler::findAll)
                        .andRoute(GET("/{id}"), blogHandler::findById)
                        .andRoute(GET("/author/{authorId}"), blogHandler::findByAuthorId)
                        .andRoute(POST("").and(contentType(APPLICATION_JSON)), blogHandler::save)
                        .andRoute(DELETE("/{id}"), blogHandler::delete)
            );
    }

    @Bean
    public RouterFunction<ServerResponse> authorRoutes(AuthorHandler authorHandler){
        return RouterFunctions.nest(RequestPredicates.path("/authors"),
                RouterFunctions
                .route(GET(""), authorHandler::findAll)
                .andRoute(GET("/by-email/{email}"), authorHandler::findByEmail)
                .andRoute(GET("/query"), authorHandler::findByEmail)
                .andRoute(GET("/{id}"), authorHandler::findById)
                .andRoute(POST("").and(accept(APPLICATION_JSON)),authorHandler::save)
                .andRoute(DELETE("/{id}"), authorHandler::delete)
            );
    }

    @Bean
    public RouterFunction<ServerResponse> postRoutes(PostHandler postHandler){
        return RouterFunctions.nest(RequestPredicates.path("/posts"),
                RouterFunctions.route(GET(""), postHandler::findAll)
                .andRoute(POST("").and(accept(APPLICATION_JSON)),postHandler::save)
                .andRoute(GET("/blog/{authorId}"), postHandler::findByBlogId)
            );
    }

    @Bean
    public RouterFunction<ServerResponse> commentRoutes(CommentHandler commentHandler){
        return RouterFunctions.nest(RequestPredicates.path("/comments"),
                RouterFunctions.route(GET(""), commentHandler::findAll)
                        .andRoute(POST("").and(accept(APPLICATION_JSON)),commentHandler::save)
                        .andRoute(GET("/post/{authorId}"), commentHandler::findByPostId)
        );
    }

    @Bean
    public RouterFunction<ServerResponse> reactionRoutes(ReactionHandler reactionHandler){
        return RouterFunctions.nest(RequestPredicates.path("/reactions"),
                RouterFunctions.route(GET(""), reactionHandler::findAll)
                        .andRoute(POST("").and(accept(APPLICATION_JSON)),reactionHandler::save)
                        .andRoute(DELETE("/{id}"), reactionHandler::delete)
        );
    }

    @Bean
    public RouterFunction<ServerResponse> userRoutes(UserHandler userHandler){
        return RouterFunctions.nest(RequestPredicates.path("/users"),
                RouterFunctions.route(GET(""), userHandler::findAll)
                        .andRoute(POST("").and(accept(APPLICATION_JSON)),userHandler::save)
                        .andRoute(POST("/auth").and(accept(APPLICATION_JSON)),userHandler::auth)
        );
    }
}
