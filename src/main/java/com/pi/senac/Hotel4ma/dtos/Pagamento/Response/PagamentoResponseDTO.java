package com.pi.senac.Hotel4ma.dtos.Pagamento.Response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pi.senac.Hotel4ma.dtos.Cliente.Response.ClienteResumoDTO;
import com.pi.senac.Hotel4ma.dtos.Reserva.Response.ReservaResumoDTO;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PagamentoResponseDTO(
        Long id,
        String tipoPagamento,   // ok, converte Enum -> String
        BigDecimal valorTotal,
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")// melhor que Double
        LocalDateTime horaPagamento,
        ClienteResumoDTO cliente,
        ReservaResumoDTO reserva
) {
}
