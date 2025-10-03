package com.pi.senac.Hotel4ma.dtos.Reserva.Response;

import com.pi.senac.Hotel4ma.dtos.Cliente.Response.ClienteResumoDTO;
import com.pi.senac.Hotel4ma.dtos.Funcionario.Response.FuncionarioResumoDTO;
import com.pi.senac.Hotel4ma.dtos.Pagamento.Response.PagamentoResponseDTO;
import com.pi.senac.Hotel4ma.enums.StatusReserva;
import com.pi.senac.Hotel4ma.enums.TipoPagamento;
import com.pi.senac.Hotel4ma.model.Cliente;
import com.pi.senac.Hotel4ma.model.Funcionario;
import com.pi.senac.Hotel4ma.model.InstalacaoAlugavel;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record ReservaResponseDTO(
        Long id,
        String tipoPagamento,
        BigDecimal valorTotal,
        String status,
        LocalDate checkIn,
        LocalDate checkOut,
        LocalDateTime dataCadastro,
        ClienteResumoDTO cliente,
        FuncionarioResumoDTO funcionario,
        Long idInstalacao,
        PagamentoResponseDTO pagamento

) {
}
