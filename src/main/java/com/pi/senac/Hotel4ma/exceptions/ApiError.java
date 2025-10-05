package com.pi.senac.Hotel4ma.exceptions;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;
import java.time.LocalDateTime;
import java.util.List;


//versão aprimorada com Record , para aplicação do principio DRY - Dont repeat Yourself
public record ApiError(
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
        LocalDateTime timestamp,
        int status,
        String error,
        String message,
        String path,
        List<ApiValidationError> validationErrors //captura erros de campos especificos agora
) {

    public static ApiError of(HttpStatus status, String message, String path){
        return new ApiError(LocalDateTime.now(), status.value(), status.getReasonPhrase(), message, path, null);
    }
}
