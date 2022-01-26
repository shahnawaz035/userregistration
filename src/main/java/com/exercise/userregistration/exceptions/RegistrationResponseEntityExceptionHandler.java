package com.exercise.userregistration.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
@RestController
public class RegistrationResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public final ResponseEntity<ErrorDetails> handleException(ResponseStatusException ex) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getReason(), ex.getStatus());
        return new ResponseEntity<>(errorDetails, ex.getStatus());
    }
}
