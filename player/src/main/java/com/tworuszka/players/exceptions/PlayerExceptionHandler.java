package com.tworuszka.players.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class PlayerExceptionHandler {

    @ExceptionHandler(value = PlayerNotFoundException.class)
    public ResponseEntity<ErrorMessage> ResourceNotFoundException(PlayerNotFoundException e, WebRequest request) {
        ErrorMessage errorMessage = new ErrorMessage(
                HttpStatus.NOT_FOUND.value(),
                new Date(),
                e.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = UsernameExistException.class)
    public ResponseEntity<ErrorMessage> ResourceExistsException(UsernameExistException e, WebRequest request) {
        ErrorMessage errorMessage = new ErrorMessage(
                HttpStatus.CONFLICT.value(),
                new Date(),
                e.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(errorMessage, HttpStatus.CONFLICT);
    }
}
