package com.pi.senac.Hotel4ma;

import com.pi.senac.Hotel4ma.exceptions.ReservaDataConflitanteException;
import com.pi.senac.Hotel4ma.repository.ReservaRepository;
import com.pi.senac.Hotel4ma.service.ReservaService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class VerificacaoDeConflitoReservaTest {

    @Test
    void deveLancarExcecaoQuandoHaConflitoDeReserva() throws Exception {
        // Mock do repository
        ReservaRepository repositoryMock = Mockito.mock(ReservaRepository.class);
        Mockito.when(repositoryMock.existsReservaConflitante(
                Mockito.anyLong(),
                Mockito.any(LocalDateTime.class),
                Mockito.any(LocalDateTime.class),
                Mockito.anyLong()
        )).thenReturn(true);

        // Instância do service com o mock
        ReservaService service = new ReservaService(repositoryMock, null, null, null, null);

        // Dados de entrada
        Long instalacaoId = 1L;
        LocalDateTime checkIn = LocalDateTime.of(2025, 10, 21, 14, 0);
        LocalDateTime checkOut = LocalDateTime.of(2025, 10, 22, 10, 0);
        Long reservaId = 99L;

        // Acesso ao metodo privado
        Method metodo = ReservaService.class.getDeclaredMethod("verificarConflitoReserva", Long.class, LocalDateTime.class, LocalDateTime.class, Long.class);
        metodo.setAccessible(true);

        // Verifica se a exceção correta é lançada
        try {
            metodo.invoke(service, instalacaoId, checkIn, checkOut, reservaId);
            fail("Esperava-se ReservaDataConflitanteException, mas nenhuma exceção foi lançada.");
        } catch (InvocationTargetException e) {
            Throwable causa = e.getCause();
            assertTrue(causa instanceof ReservaDataConflitanteException, "Exceção lançada não é do tipo esperado.");
            assertTrue(causa.getMessage().contains("Conflito de reserva"), "Mensagem da exceção não contém o texto esperado.");
        }
    }
}