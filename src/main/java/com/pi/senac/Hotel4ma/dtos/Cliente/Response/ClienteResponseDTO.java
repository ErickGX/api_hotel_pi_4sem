package com.pi.senac.Hotel4ma.dtos.Cliente.Response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pi.senac.Hotel4ma.enums.TipoCliente;


import java.time.LocalDateTime;

public record ClienteResponseDTO(
        Long id,

        String nome,

        String email,

        String telefone,

        String endereco,

        String cpf,
        String cnpj,
        // String documento,  // Pode conter CPF ou CNPJ, dependendo do tipo
        TipoCliente tipoCliente,

        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
        LocalDateTime dataCadastro    // Data de criação do registro

//        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
//        LocalDateTime dataAtualizacao // Data da última atualização
) {
}
