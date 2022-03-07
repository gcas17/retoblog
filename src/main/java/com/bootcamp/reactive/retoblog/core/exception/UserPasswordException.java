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
public class UserPasswordException extends RuntimeException {

    private HttpStatus status  = HttpStatus.UNAUTHORIZED;
    private String message;

    public UserPasswordException(String message){
        this.message = message;
    }
}
