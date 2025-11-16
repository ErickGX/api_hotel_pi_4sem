package com.pi.senac.Hotel4ma.seeds;

import com.github.javafaker.Faker;
import com.pi.senac.Hotel4ma.dtos.Reserva.Request.ReservaRequest;
import com.pi.senac.Hotel4ma.enums.TipoPagamento;
import com.pi.senac.Hotel4ma.service.ReservaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class ReservaSeeder {

    private final ReservaService reservaService;
    private final Faker faker = new Faker(Locale.forLanguageTag("pt-BR"));

    private final Random random = new Random();

    private static final int QTD_RESERVAS = 100;

    private static final int CLIENTE_MIN = 1;
    private static final int CLIENTE_MAX = 199;

    private static final int FUNCIONARIO_MIN = 1;
    private static final int FUNCIONARIO_MAX = 50;

    private static final int INSTALACAO_MIN = 1;
    private static final int INSTALACAO_MAX = 4;

    // MAPA PARA CONTROLAR OS INTERVALOS DE CADA INSTALAÇÃO
    private final HashMap<Long, List<Intervalo>> agenda = new HashMap<>();

    public void populate() {

        if (reservaService.existsData()) {
            log.info("ReservaSeeder ignorado — já existem reservas cadastradas.");
            return;
        }

        log.info("Iniciando ReservaSeeder...");

        int count = 0;

        while (count < QTD_RESERVAS) {

            try {
                ReservaRequest request = gerarReservaSemConflito();
                reservaService.save(request);

                log.info("Reserva {} criada", count + 1);
                count++;

            } catch (Exception e) {
                log.warn("Falha ao gerar reserva (tentando novamente): {}", e.getMessage());
            }
        }

        log.info("ReservaSeeder finalizado.");
    }

    /// ------------- LÓGICA NOVA SEM CONFLITOS (1-3 DIAS) -------------
    private ReservaRequest gerarReservaSemConflito() {

        Long instalacaoId = gerarRange(INSTALACAO_MIN, INSTALACAO_MAX);
        agenda.putIfAbsent(instalacaoId, new ArrayList<>());

        LocalDateTime checkIn;
        LocalDateTime checkOut;

        int tentativas = 0;

        do {
            tentativas++;
            if (tentativas > 500) {
                throw new RuntimeException("Falha ao gerar reserva sem conflito após muitas tentativas.");
            }

            // Duração ENTRE 1 E 3 DIAS (NOVA REGRA)
            int dias = faker.number().numberBetween(1, 3); // 1, 2 ou 3

            checkIn = LocalDateTime.now()
                    .plusDays(faker.number().numberBetween(1, 60))
                    .withHour(faker.number().numberBetween(8, 22))
                    .withMinute(0);

            checkOut = checkIn.plusDays(dias);

        } while (colide(agenda.get(instalacaoId), checkIn, checkOut));

        // Registro do intervalo seguro
        agenda.get(instalacaoId).add(new Intervalo(checkIn, checkOut));

        Long clienteId = gerarRange(CLIENTE_MIN, CLIENTE_MAX);
        Long funcionarioId = gerarRange(FUNCIONARIO_MIN, FUNCIONARIO_MAX);

        TipoPagamento tipoPagamento = faker.options().option(
                TipoPagamento.PIX,
                TipoPagamento.CREDITO,
                TipoPagamento.DEBITO,
                TipoPagamento.DINHEIRO
        );

        return new ReservaRequest(
                tipoPagamento,
                checkIn,
                checkOut,
                clienteId,
                funcionarioId,
                instalacaoId
        );
    }

    // VERIFICA SOBREPOSIÇÃO
    private boolean colide(List<Intervalo> lista, LocalDateTime inicio, LocalDateTime fim) {
        for (Intervalo i : lista) {
            boolean sobrepoe =
                    !fim.isBefore(i.inicio) &&
                            !inicio.isAfter(i.fim);

            if (sobrepoe) return true;
        }
        return false;
    }

    private Long gerarRange(int min, int max) {
        return (long) (random.nextInt(max - min + 1) + min);
    }

    // INTERVALO
    private static class Intervalo {
        final LocalDateTime inicio;
        final LocalDateTime fim;

        Intervalo(LocalDateTime inicio, LocalDateTime fim) {
            this.inicio = inicio;
            this.fim = fim;
        }
    }
}