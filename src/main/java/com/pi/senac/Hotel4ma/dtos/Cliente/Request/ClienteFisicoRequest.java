package com.pi.senac.Hotel4ma.dtos.Cliente.Request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

public record ClienteFisicoRequest(
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
        String cpf,

        @NotBlank(message = "CEP é obrigatório")
        @Pattern(regexp = "^[0-9]{8}$", message = "CEP deve conter exatamente 8 dígitos numéricos")
        String cep,

        @NotBlank(message = "Endereço é obrigatório")
        @Size(min = 5, max = 255, message = "Endereço deve ter entre 5 e 255 caracteres")
        String logradouro,

        @NotBlank(message = "Número é obrigatório")
        @Size(max = 10, message = "Número deve ter no máximo 10 caracteres")
        String numero,

        @Size(max = 100, message = "complemento deve ter no máximo 100 caracteres")
        String complemento,

        @NotBlank(message = "Bairro é obrigatório")
        @Size(min = 2, max = 60, message = "Bairro deve ter entre 3 e 60 caracteres")
        String bairro,

        @NotBlank(message = "Localidade é obrigatória")
        @Size(min = 3, max = 60, message = "Localidade deve ter entre 3 e 60 caracteres")
        String localidade,

        @NotBlank(message = "UF é obrigatório")
        @Pattern(regexp = "^(AC|AL|AP|AM|BA|CE|DF|ES|GO|MA|MT|MS|MG|PA|PB|PR|PE|PI|RJ|RN|RS|RO|RR|SC|SP|SE|TO)$", message = "UF inválido")
        String uf

        ) {}
