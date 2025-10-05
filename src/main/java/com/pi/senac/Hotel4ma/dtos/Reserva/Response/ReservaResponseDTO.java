package com.pi.senac.Hotel4ma.dtos.Reserva.Response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pi.senac.Hotel4ma.dtos.Cliente.Response.ClienteResumoDTO;
import com.pi.senac.Hotel4ma.dtos.Funcionario.Response.FuncionarioResumoDTO;
import com.pi.senac.Hotel4ma.dtos.Instalacao.Response.InstalacaoResumoDTO;
import com.pi.senac.Hotel4ma.enums.StatusReserva;
import com.pi.senac.Hotel4ma.enums.TipoPagamento;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ReservaResponseDTO(
        Long id,
        TipoPagamento tipoPagamento,
        BigDecimal valorTotal,
        StatusReserva status,
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
        LocalDateTime checkIn,
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
        LocalDateTime checkOut,
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
        LocalDateTime dataCadastro,
        ClienteResumoDTO cliente,
        FuncionarioResumoDTO funcionario,
        InstalacaoResumoDTO instalacaoAlugavel

) {
}
