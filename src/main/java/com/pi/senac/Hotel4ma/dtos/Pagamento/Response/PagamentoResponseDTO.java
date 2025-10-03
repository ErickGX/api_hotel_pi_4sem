package com.pi.senac.Hotel4ma.dtos.Pagamento.Response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PagamentoResponseDTO(
        Long id,
        String tipoPagamento,   // ok, converte Enum -> String
        BigDecimal valor,       // melhor que Double
        LocalDateTime horaPagamento
) {
}
