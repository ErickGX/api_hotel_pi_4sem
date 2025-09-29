package com.pi.senac.Hotel4ma.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ApiError> handleResourceNotFound(ResourceNotFoundException ex, HttpServletRequest request) {
        ApiError errorApi = new ApiError(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                "Recurso não Encontrado",
                ex.getMessage(),
                request.getRequestURI(),
                null
        );
        return new ResponseEntity<>(errorApi, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateEmailException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ApiError> handleDuplicateEmail(DuplicateEmailException ex, HttpServletRequest request) {
        ApiError errorApi = new ApiError(
                LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                "Registro Duplicado",
                ex.getMessage(),
                request.getRequestURI(),
                null
        );
        return new ResponseEntity<>(errorApi, HttpStatus.CONFLICT);
    }


    @ExceptionHandler(DuplicateCpfException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ApiError> handleDuplicateCpf(DuplicateCpfException ex, HttpServletRequest request) {
        ApiError errorApi = new ApiError(
                LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                "Registro Duplicado",
                ex.getMessage(),
                request.getRequestURI(),
                null
        );
        return new ResponseEntity<>(errorApi, HttpStatus.CONFLICT);
    }



    @ExceptionHandler(DuplicateCnpjException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ApiError> handleDuplicateEmail(DuplicateCnpjException ex, HttpServletRequest request) {
        ApiError errorApi = new ApiError(
                LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                "Registro Duplicado",
                ex.getMessage(),
                request.getRequestURI(),
                null
        );
        return new ResponseEntity<>(errorApi, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiError> handleBadRequest(BadRequestException ex, HttpServletRequest request) {
        ApiError errorApi = new ApiError(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Dados Inválidos",
                ex.getMessage(),
                request.getRequestURI(),
                null
        );
        return new ResponseEntity<>(errorApi, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidationErrors(MethodArgumentNotValidException ex, WebRequest request) {

        // 1. Coleta os erros de campo
        List<ApiValidationError> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> new ApiValidationError(
                        fieldError.getField(),
                        fieldError.getDefaultMessage()
                ))
                .collect(Collectors.toList());

        // 2. Monta o objeto de erro
        ApiError apiError = new ApiError(
                LocalDateTime.now(),
                HttpStatus.UNPROCESSABLE_ENTITY.value(), // ou BAD_REQUEST se preferir
                "Erro de Validação",
                "Existem campos inválidos na requisição.",
                request.getDescription(false),
                errors
        );

        // 3. Retorna a resposta com o status correto
        return ResponseEntity.unprocessableEntity().body(apiError);
    }


}
