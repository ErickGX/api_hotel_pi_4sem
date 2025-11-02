package com.pi.senac.Hotel4ma.dtos.Funcionario.Request;

import com.pi.senac.Hotel4ma.enums.CargoFuncionario;
import jakarta.validation.constraints.*;

public record FuncionarioUpdateRequest(
        @Size(min = 3, max = 150, message = "Nome deve conter no mínimo 3 e no máximo 150 caracteres")
        @NotBlank(message = "Campo nome é obrigatório")
        String nome,

        @NotBlank(message = "Campo email é obrigatório")
        @Email(message = "Campo email inválido")
        String email,

        @NotBlank(message = "Telefone é obrigatório")
        @Pattern(regexp = "^[0-9]{11}$", message = "Telefone deve conter exatamente 11 dígitos numéricos")
        String telefone,

        //possivel mudança para ENUM FUTURAMENTE
        @NotNull(message = "Endereço é obrigatório")
        @Size(min = 5, max = 50, message = "Endereço deve ter entre 5 e 50 caracteres")
        String cargo
) {
}
