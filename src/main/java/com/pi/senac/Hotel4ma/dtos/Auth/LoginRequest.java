package com.pi.senac.Hotel4ma.dtos.Auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @Email(message = "Formato de email inválido")
        @NotBlank(message = "Campo email é obrigatório")
        String email,
        @NotBlank(message = "Campo senha é obrigatório")
        String senha
) {}
