package com.pi.senac.Hotel4ma.dtos.Cliente.Request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CNPJ;

public record ClienteJuridicoRequest(

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

        @NotBlank(message = "Endereço é obrigatório")
        @Size(min = 5, max = 255, message = "Endereço deve ter entre 5 e 255 caracteres")
        String endereco,

        @NotBlank(message = "CNPJ é obrigatório")
        @Pattern(regexp = "^[0-9]{14}$", message = "CNPJ deve conter exatamente 14 dígitos numéricos")
        @CNPJ(message = "CNPJ inválido ou mal formatado")
        String cnpj
) {}
