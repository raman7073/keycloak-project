package com.fiftyfive.authserver.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.METHOD_FAILURE)
public class LogoutFailureException extends RuntimeException {
    private final String message;

    public LogoutFailureException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
    }
}


