package com.fiftyfive.authserver.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidUserNamePasswordException extends RuntimeException {
    private final String message;

    public InvalidUserNamePasswordException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
    }
}
