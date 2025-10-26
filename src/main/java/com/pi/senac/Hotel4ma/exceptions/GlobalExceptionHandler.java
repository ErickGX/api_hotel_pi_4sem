package com.pi.senac.Hotel4ma.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.lang.Exception;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);




    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<ApiError> handleAuthorizationDenied(AuthorizationDeniedException ex, HttpServletRequest request){

        log.warn("Tentativa de acesso não autorizado: {}", ex.getMessage());

        ApiError errorApi = new ApiError(
                LocalDateTime.now(),
                HttpStatus.FORBIDDEN.value(),
                "Acesso Negado",
                "Você não possui permissão para acessar este recurso." + ex.getMessage(),
                request.getRequestURI(),
                null
        );
        return new ResponseEntity<>(errorApi, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(AuthenticationException.class) // 2. Especifique a exceção base
    public ResponseEntity<ApiError> handleAuthenticationException(
            AuthenticationException ex, HttpServletRequest request) { // Use HttpServletRequest

        // Log mais detalhado, pois AuthenticationException tem subclasses úteis
        log.warn("Falha na autenticação: {} - Causa: {}", ex.getClass().getSimpleName(), ex.getMessage());

        // Cria o seu DTO de erro padrão
        ApiError errorApi = new ApiError(
                LocalDateTime.now(), // Timestamp
                HttpStatus.UNAUTHORIZED.value(), // Status 401
                "Não Autorizado", // Título do erro
                "Falha na autenticação: " + ex.getMessage(), // Mensagem (pode vazar detalhes, ajuste se necessário)
                request.getRequestURI(), // Caminho da requisição
                null // Lista de erros
        );

        // Retorna o ResponseEntity
        return new ResponseEntity<>(errorApi, HttpStatus.UNAUTHORIZED);
    }


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
    public ResponseEntity<ApiError> handleDuplicateCnpj(DuplicateCnpjException ex, HttpServletRequest request) {
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



    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiError> handleIllegalArgument(IllegalArgumentException ex, HttpServletRequest request) {
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


    @ExceptionHandler(ReservaDataConflitanteException.class)
    public ResponseEntity<ApiError> handleReservaDataConflitante(ReservaDataConflitanteException ex, HttpServletRequest request) {
        ApiError errorApi = new ApiError(
                LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                "Conflito de Agendamento",
                ex.getMessage(),
                request.getRequestURI(),
                null
        );
        return new ResponseEntity<>(errorApi, HttpStatus.CONFLICT);
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


    @ExceptionHandler(java.lang.Exception.class)
    public ResponseEntity<ApiError> handleGenericException(Exception ex, HttpServletRequest request) {
        ApiError errorApi = new ApiError(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Erro Interno do Servidor, Contate a Administração",
                "Ocorreu um erro inesperado. Tente novamente mais tarde.",
                request.getRequestURI(),
                null
        );
        return new ResponseEntity<>(errorApi, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
