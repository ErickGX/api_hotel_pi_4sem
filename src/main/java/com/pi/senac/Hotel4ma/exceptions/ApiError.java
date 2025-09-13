package com.pi.senac.Hotel4ma.exceptions;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;


//versão aprimorada com Record , para aplicação do principio DRY - Dont repeat Yourself
public record ApiError(
        LocalDateTime timestamp,
        int status,
        String error,
        String message,
        String path
) {

    public static ApiError of(HttpStatus status, String message, String path){
        return new ApiError(LocalDateTime.now(), status.value(), status.getReasonPhrase(), message, path);
    }
}
