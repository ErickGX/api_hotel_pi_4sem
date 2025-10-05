package com.pi.senac.Hotel4ma.dtos.Instalacao.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record InstalacaoRequest(

        @Size(min = 3, max = 150, message = "Nome deve conter no mínimo 3 e no máximo 150 caracteres")
        @NotBlank(message = "Campo nome é obrigatório")
        String nome,

        @NotBlank(message = "Campo disponibilidade é obrigatório")
        Boolean isDisponivel,

        @Size(min = 3, max = 150, message = "Nome deve conter no mínimo 3 e no máximo 150 caracteres")
        @NotBlank(message = "Campo nome é obrigatório")
        String descricao,

        @NotBlank(message = "Campo tipo é obrigatório")
        String tipo,       // "SAUNA", "QUARTO", "AUDITORIO", Uso da factory

        @NotBlank(message = "Categoria é obrigatório")
        String categoria,  // TipoSauna, TipoQuarto, etc.

        String numeroQuarto, //pode ser null, caso nao seja um quarto

        @NotNull
        Long id_hotel// só se for Quarto
) {
}
