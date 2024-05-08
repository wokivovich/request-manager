package com.anikulin.manager.controller;

import com.anikulin.manager.util.exception.RequestNotFoundException;
import com.anikulin.manager.util.exception.RequestStatusException;
import com.anikulin.manager.util.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RequestNotFoundException.class)
    public ResponseEntity<String> requestNotFoundException(RequestNotFoundException exception) {

        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RequestStatusException.class)
    public ResponseEntity<String> requestStatusException(RequestStatusException exception) {

        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> userNotFoundException(UserNotFoundException exception) {

        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
