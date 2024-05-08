package com.anikulin.manager.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
public class RequestStatusException extends RuntimeException {
    public RequestStatusException(String message) {
        super(message);
    }
}
