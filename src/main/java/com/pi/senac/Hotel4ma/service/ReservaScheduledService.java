package com.pi.senac.Hotel4ma.service;

import com.pi.senac.Hotel4ma.enums.StatusReserva;
import com.pi.senac.Hotel4ma.model.Reserva;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservaScheduledService {

    private static final Logger log = LoggerFactory.getLogger(ReservaScheduledService.class);
    private final ReservaService reservaService;

    /**
     * Tarefa agendada (Scheduled Task) para finalizar reservas que já passaram
     * da data de check-out.
     *
     * A expressão "cron" "0 0 * * * *" significa:
     * "Execute no segundo 0, do minuto 0, de toda hora, todo dia."
     * (Ou seja, roda uma vez por hora, na hora cheia).
     */
    //fixedRate = 40000 para testes roda em 40seg
    @Scheduled(cron = "0 0 * * * *") // Roda de hora em hora
    @Transactional
    public void finalizarReservasPassadas() {
        log.info("[SCHEDULED TASK] Iniciando verificação de reservas para finalizar...");


        // 1. Busca no banco as reservas elegíveis
        List<Reserva> reservasParaFinalizar = reservaService.finalizarReservasAutomaticamente();

        if (reservasParaFinalizar.isEmpty()) {
            log.info("[SCHEDULED TASK] Nenhuma reserva encontrada para finalizar.");
            return;
        }

        log.info("[SCHEDULED TASK] Encontradas {} reservas para marcar como FINALIZADAS.", reservasParaFinalizar.size());

        // 2. Altera o status de todas elas em memória
        for (Reserva reserva : reservasParaFinalizar) {
            log.debug("Finalizando Reserva ID: {}", reserva.getId());
            reserva.setStatusReserva(StatusReserva.FINALIZADA);
        }

        // 3. Salva todas as alterações no banco em uma única transação
        reservaService.saveFinalizadas(reservasParaFinalizar);

        log.info("[SCHEDULED TASK] Tarefa concluída. {} reservas foram atualizadas.", reservasParaFinalizar.size());
    }

}
