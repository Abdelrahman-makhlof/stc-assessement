package com.example.assessment.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class SystemExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Error> handleOutBoundException(Exception ex) {
        var apiError = new Error(LocalDateTime.now(), ex.getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
