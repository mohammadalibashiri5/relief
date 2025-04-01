package com.mohammad.relief.controller;

import com.mohammad.relief.data.dto.response.ApiErrorResponse;
import com.mohammad.relief.exception.ReliefApplicationException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ReliefApplicationException.class)
    public ResponseEntity<ApiErrorResponse> handleReliefException(ReliefApplicationException ex) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleEntityNotFound(EntityNotFoundException ex) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleGlobalException(Exception ex) {
        return buildErrorResponse("An unexpected error occurred "+ ex.getMessage() , HttpStatus.INTERNAL_SERVER_ERROR );
    }

    private ResponseEntity<ApiErrorResponse> buildErrorResponse(String message, HttpStatus status) {
        return ResponseEntity.status(status).body(new ApiErrorResponse(status.value(), message, LocalDateTime.now()));
    }
}
