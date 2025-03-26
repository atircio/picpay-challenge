package com.atircio.pickpay.exceptions;

import com.atircio.pickpay.dtos.ErrorResponseDto;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponseDto>  handleEntityNotFoundException(EntityNotFoundException exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).
                body(new ErrorResponseDto(HttpStatus.NOT_FOUND.value(), exception.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleValidationException(MethodArgumentNotValidException ex) {
        HashMap<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponseDto(HttpStatus.BAD_REQUEST.value(), "Validation failed", errors));
    }

    @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity<ErrorResponseDto>  handleInsufficientBalanceException(InsufficientBalanceException exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).
                body(new ErrorResponseDto(HttpStatus.BAD_REQUEST.value(), exception.getMessage()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseDto>  handleInsufficientBalanceException(IllegalArgumentException exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).
                body(new ErrorResponseDto(HttpStatus.BAD_REQUEST.value(), exception.getMessage()));
    }

    @ExceptionHandler(TransactionFailedException.class)
    public ResponseEntity<ErrorResponseDto>  handleTransactionFailedException(TransactionFailedException exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).
                body(new ErrorResponseDto(HttpStatus.BAD_REQUEST.value(), exception.getMessage()));
    }
    @ExceptionHandler(UnauthorizedTransactionException.class)
    public ResponseEntity<ErrorResponseDto>  handleUnauthorizedTransactionException(UnauthorizedTransactionException exception){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).
                body(new ErrorResponseDto(HttpStatus.UNAUTHORIZED.value(), exception.getMessage()));
    }

    @ExceptionHandler(DuplicateEmailException.class)
    //@ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ErrorResponseDto> handleDuplicateEmailException(DuplicateEmailException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponseDto(HttpStatus.CONFLICT.value(), ex.getMessage()));
    }


}
