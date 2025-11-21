package com.pi.senac.Hotel4ma.dtos.Cliente.Request;

import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

public record ClienteUpdateRequest(
        @Size(min = 3, max = 150, message = "Nome deve conter no mínimo 3 e no máximo 150 caracteres")
        @NotBlank(message = "Campo nome é obrigatório")
        String nome,

        @NotBlank(message = "Campo email é obrigatório")
        @Email(message = "Campo email inválido")
        String email,

        @Pattern(regexp = "^[0-9]{11}$", message = "CPF deve conter exatamente 11 dígitos numéricos")
        @CPF(message = "CPF inválido ou mal formatado")
        String cpf,

        @Pattern(regexp = "^[0-9]{14}$", message = "CNPJ deve conter exatamente 14 dígitos numéricos")
        @CNPJ(message = "CNPJ inválido ou mal formatado")
        String cnpj,

        @NotBlank(message = "Telefone é obrigatório")
        @Pattern(regexp = "^[0-9]{11}$", message = "Telefone deve conter exatamente 11 dígitos numéricos")
        String telefone,

        @NotBlank(message = "CEP é obrigatório")
        @Pattern(regexp = "^[0-9]{8}$", message = "CEP deve conter exatamente 8 dígitos numéricos")
        String cep,

        @NotBlank(message = "Endereço é obrigatório")
        @Size(min = 5, max = 255, message = "Endereço deve ter entre 5 e 255 caracteres")
        String logradouro,

        @NotBlank(message = "Número é obrigatório")
        @Size(min = 1, max = 10, message = "Número deve ter entre 1 e 10 caracteres")
        String numero,

        @Size(max = 100, message = "Complemento deve ter no máximo 100 caracteres")
        String complemento,

        @NotBlank(message = "Bairro é obrigatório")
        @Size(min = 2, max = 60, message = "Bairro deve ter entre 2 e 60 caracteres")
        String bairro,

        @NotBlank(message = "Cidade é obrigatória")
        @Size(min = 2, max = 100, message = "Cidade deve ter entre 2 e 100 caracteres")
        String localidade,

        @NotBlank(message = "UF é obrigatório")
        @Pattern(regexp = "^(AC|AL|AP|AM|BA|CE|DF|ES|GO|MA|MT|MS|MG|PA|PB|PR|PE|PI|RJ|RN|RS|RO|RR|SC|SP|SE|TO)$", message = "UF inválido")
        String uf
) {

    //validação customizada para garantir que apenas um entre CPF ou CNPJ seja informado
    @AssertTrue(message = "Informe CPF ou CNPJ, mas não ambos")
    public boolean isCpfOrCnpjValido() {
        boolean cpfPreenchido = cpf != null && !cpf.isBlank();
        boolean cnpjPreenchido = cnpj != null && !cnpj.isBlank();

        return cpfPreenchido ^ cnpjPreenchido; // XOR
    }

    //validação customizada para CPF , campos opcionais não permitem pattern nas anotações
    @AssertTrue(message = "CPF inválido")
    public boolean isCpfValidoSeInformado() {
        if (cpf == null || cpf.isBlank()) return true;
        return cpf.matches("^[0-9]{11}$");
    }
    //validação customizada para CNPJ
    @AssertTrue(message = "CNPJ inválido")
    public boolean isCnpjValidoSeInformado() {
        if (cnpj == null || cnpj.isBlank()) return true;
        return cnpj.matches("^[0-9]{14}$");
    }
}
