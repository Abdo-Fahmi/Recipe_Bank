package com.Abdo_Fahmi.Recipe_Bank.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleGeneralException(Exception e) {
        return buildErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(PasswordsDoNotMatchException.class)
    public ResponseEntity<ErrorResponseDTO> handlePasswordDoesNotMatchException(Exception e) {
        return buildErrorResponse(e.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(IncorrectCredentialsException.class)
    public ResponseEntity<ErrorResponseDTO> handleIncorrectCredentials(Exception e) {
        return buildErrorResponse(e.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(EmailAlreadyInUseException.class)
    public ResponseEntity<ErrorResponseDTO> handleEmailAlreadyInUseException(EmailAlreadyInUseException e) {
        return buildErrorResponse(e.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(NameAlreadyInUseException.class)
    public ResponseEntity<ErrorResponseDTO> handleNameAlreadyInUseException(NameAlreadyInUseException e) {
        return buildErrorResponse(e.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleUserNotFoundException(UserNotFoundException e) {
        return buildErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RecipeNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleRecipeNotFoundException(UserNotFoundException e) {
        return buildErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    private ResponseEntity<ErrorResponseDTO> buildErrorResponse(String message, HttpStatus status) {
        ErrorResponseDTO errorResponseDTO = ErrorResponseDTO.builder()
                .message(message)
                .timeStamp(new Date())
                .build();

        return new ResponseEntity<>(errorResponseDTO, status);
    }
}
