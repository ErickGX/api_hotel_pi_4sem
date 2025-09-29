package com.pi.senac.Hotel4ma.dtos.Administrador.Request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

public record AdminRequestDTO(
        @Size(min = 3, max = 150, message = "Nome deve conter no mínimo 3 e no máximo 150 caracteres")
        @NotBlank(message = "Campo nome é obrigatório")
        String nome,

        @NotBlank(message = "Campo email é obrigatório")
        @Email(message = "Campo email inválido")
        String email,

        @NotBlank(message = "Telefone é obrigatório")
        @Pattern(regexp = "^[0-9]{11}$", message = "Telefone deve conter exatamente 11 dígitos numéricos")
        String telefone,

        @NotBlank(message = "Senha é obrigatória")
        @Size(min = 6, max = 30, message = "Senha deve ter entre 6 e 30 caracteres")
        String senha,

        @NotBlank(message = "CPF é obrigatório")
        @Pattern(regexp = "^[0-9]{11}$", message = "CPF deve conter exatamente 11 dígitos numéricos")
        @CPF(message = "CPF inválido ou mal formatado")
        String cpf

    ) {}
