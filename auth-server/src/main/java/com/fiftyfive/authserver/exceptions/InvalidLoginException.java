package com.fiftyfive.authserver.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class InvalidLoginException extends RuntimeException {
    private final String message;

    public InvalidLoginException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
    }
}
