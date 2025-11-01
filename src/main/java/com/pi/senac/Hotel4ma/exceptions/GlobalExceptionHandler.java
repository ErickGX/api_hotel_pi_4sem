package com.pi.senac.Hotel4ma.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
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




    /**
     * Captura erros de niveis de acesso a recursos protegidos  (ex: Cliente acessando recursos restristos a admins)
     * Exceção lançada pelo Spring Security quando o usuário autenticado
     * tenta acessar um recurso para o qual não tem permissão.
     * Retorna 403 FORBIDDEN.
     */
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


    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiError> handleAuthenticationException(
            AuthenticationException ex, HttpServletRequest request) {

        // Log mais detalhado, pois AuthenticationException tem subclasses úteis
        log.warn("Falha na autenticação: {} - Causa: {}", ex.getClass().getSimpleName(), ex.getMessage());


        ApiError errorApi = new ApiError(
                LocalDateTime.now(),
                HttpStatus.UNAUTHORIZED.value(), // Status 401
                "Não Autorizado", // Título do erro
                "Falha na autenticação: " + ex.getMessage(),
                request.getRequestURI(), // Caminho da requisição
                null // Lista de erros
        );

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
                "Formatos de dados inválidos ou argumentos incorretos: ",
                request.getRequestURI(),
                null
        );
        return new ResponseEntity<>(errorApi, HttpStatus.BAD_REQUEST);
    }


    /**
     * Captura erros de desserialização do JSON  que são lançados antes de
     * a validação (@Valid) ser executada.
     * Retorna 400 Bad Request.
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiError> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex, HttpServletRequest request) {

        String mensagem = "Requisição JSON mal formatada ou com valor inválido.";

        // Tenta extrair a causa raiz (que é a IllegalArgumentException)
        Throwable cause = ex.getCause();
        if (cause != null && cause.getMessage() != null) {
            // Ex: "No enum constant com.pi.senac.Hotel4ma.enums.TipoEspacos.QUINTAL"
            if (cause.getMessage().contains("No enum constant")) {
                mensagem = "Valor inválido fornecido para um campo de seleção (enum).";
            }
        }

        ApiError errorApi = new ApiError(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Requisição Inválida",
                mensagem, // 3. Mensagem mais amigável
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


    /**
     * Metodo para captura de erros das DTOS e bindagem dos campos com erros
     * retona 422 UNPROCESSABLE_ENTITY
     * **/
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
