package com.nagarro.userapp.dto;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ApiErrorResponseDTO {
    private String message;
    private int code;
    private LocalDateTime timestamp;

    public ApiErrorResponseDTO(String message, HttpStatus  code) {
        this.message = message;
        this.code = code.value();
        this.timestamp = LocalDateTime.now();
    }

}
