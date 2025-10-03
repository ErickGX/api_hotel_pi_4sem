package com.pi.senac.Hotel4ma.dtos.Funcionario.Response;

public record FuncionarioResumoDTO(
        Long id,
        String nome,
        String email,
        String telefone,
        String cargo
) {
}
