package com.bootcamp.reactive.retoblog.core.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReactionNotFoundException extends RuntimeException {
    private HttpStatus status  = HttpStatus.NOT_FOUND;
    private String message;

    public ReactionNotFoundException(String message){
        this.message = message;
    }
}
