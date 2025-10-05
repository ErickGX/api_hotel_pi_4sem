package com.pi.senac.Hotel4ma.dtos.Cliente.Response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pi.senac.Hotel4ma.dtos.Reserva.Response.ReservaResumoDTO;
import com.pi.senac.Hotel4ma.enums.TipoCliente;


import java.time.LocalDateTime;
import java.util.List;

public record ClienteResponseDTO(
        Long id,
        String nome,
        String email,
        String telefone,
        String cep,
        String logradouro,
        String numero,
        String complemento,
        String bairro,
        String localidade,
        String uf,
        String cpf,
        String cnpj,
        // String documento,  // Pode conter CPF ou CNPJ, dependendo do tipo
        TipoCliente tipoCliente,
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
        LocalDateTime dataCadastro,    // Data de criação do registro
        List<ReservaResumoDTO> reservas
) {
}
