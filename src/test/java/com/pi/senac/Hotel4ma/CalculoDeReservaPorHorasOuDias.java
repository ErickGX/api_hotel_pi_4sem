package com.pi.senac.Hotel4ma;

import com.pi.senac.Hotel4ma.enums.*;
import com.pi.senac.Hotel4ma.model.*;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Method;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculoDeReservaPorHorasOuDias {

    private BigDecimal invocarCalculoCustoTotal(InstalacaoAlugavel instalacao, int multiplicador) throws Exception {
        Method metodo = InstalacaoAlugavel.class.getDeclaredMethod("calcularCustoTotal", int.class);
        metodo.setAccessible(true);
        return (BigDecimal) metodo.invoke(instalacao, multiplicador);
    }

    @Test
    void testCalculoCustoTotalParaSpaComTipoDetox() throws Exception {
        Spa spa = new Spa();
        spa.setPrecoBase(new BigDecimal("200.00"));
        spa.setTipoSpa(TipoSpa.DETOX); // fator 1.2

        BigDecimal resultado = invocarCalculoCustoTotal(spa, 2); // 200 * 1.2 * 2 = 480
        BigDecimal esperado = new BigDecimal("480.000");

        assertEquals(esperado, resultado);
    }

    @Test
    void testCalculoCustoTotalParaSaunaComTipoVapor() throws Exception {
        Sauna sauna = new Sauna();
        sauna.setPrecoBase(new BigDecimal("200.00"));
        sauna.setTipoSauna(TipoSauna.VAPOR); // fator 1.6

        BigDecimal resultado = invocarCalculoCustoTotal(sauna, 1); // 200 * 1.6 * 1 = 320
        BigDecimal esperado = new BigDecimal("320.000");

        assertEquals(esperado, resultado);
    }

    @Test
    void testCalculoCustoTotalParaSalaoEventosComTipoCorporativo() throws Exception {
        SalaoDeEventos salaoDeEventos = new SalaoDeEventos();
        salaoDeEventos.setPrecoBase(new BigDecimal("1000.00"));
        salaoDeEventos.setTipoSalaoEventos(TipoSalaoEventos.CORPORATIVO); // fator 3

        BigDecimal resultado = invocarCalculoCustoTotal(salaoDeEventos, 6); // 1000 * 3 * 6 = 18000
        BigDecimal esperado = new BigDecimal("18000.000");

        assertEquals(esperado, resultado);
    }

    @Test
    void testCalculoCustoTotalParaQuartoComTipoExecutivo() throws Exception {
        Quarto quarto = new Quarto();
        quarto.setPrecoBase(new BigDecimal("1000.00"));
        quarto.setTipoQuarto(TipoQuarto.EXECUTIVO); // fator 2

        BigDecimal resultado = invocarCalculoCustoTotal(quarto, 3); // 1000 * 2 * 3 = 6000
        BigDecimal esperado = new BigDecimal("6000.000");

        assertEquals(esperado, resultado);
    }
}