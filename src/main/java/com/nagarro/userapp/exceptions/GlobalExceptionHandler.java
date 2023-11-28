package com.nagarro.userapp.exceptions;

import com.nagarro.userapp.dto.ApiErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ApiErrorResponseDTO handleErrorResponse(Exception ex){
        String msg = ex.getMessage();
        ApiErrorResponseDTO response = ApiErrorResponseDTO.builder()
                .message(msg)
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .timestamp(LocalDateTime.now())
                .build();
        return response;
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ApiErrorResponseDTO handleResourceNotFound(ResourceNotFoundException ex){
        String msg = ex.getMessage();
        ApiErrorResponseDTO response = ApiErrorResponseDTO.builder()
                .message(msg)
                .code(HttpStatus.NOT_FOUND.value())
                .timestamp(LocalDateTime.now())
                .build();
        return response;
    }

    @ExceptionHandler(ValidationException.class)
    public ApiErrorResponseDTO handleValidationException(ValidationException ex){
        String msg = ex.getMessage();
        ApiErrorResponseDTO response = ApiErrorResponseDTO.builder()
                .message(msg)
                .code(HttpStatus.BAD_REQUEST.value())
                .timestamp(LocalDateTime.now())
                .build();
        return response;
    }
}
