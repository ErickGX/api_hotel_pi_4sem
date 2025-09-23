package com.pi.senac.Hotel4ma.dtos.Instalacao.Request;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record InstalacaoRequest(
        String nome,
        BigDecimal precoBase,
        Boolean isDisponivel,
        String descricao,
        String tipo,       // "SAUNA", "QUARTO", "AUDITORIO", Uso da factory
        String categoria,  // TipoSauna, TipoQuarto, etc.
        String numeroQuarto, //pode ser null, caso nao seja um quarto
        @NotNull
        Long id_hotel// só se for Quarto
) {
}
