package com.pi.senac.Hotel4ma.dtos.InstalacaoAlugavel.spa.request;

import com.pi.senac.Hotel4ma.dtos.InstalacaoAlugavel.dtoGenerics.InstalacaoAlugavelBaseDTO;
import com.pi.senac.Hotel4ma.dtos.InstalacaoAlugavel.dtoGenerics.InstalacaoAlugavelDTO;
import com.pi.senac.Hotel4ma.enums.TipoSpa;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record InstalacaoCustoSpaRequestDTO(
        @NotNull(message = "Tipo do spa é obrigatório")
        TipoSpa tipoSpa,

        @Valid
        @NotNull(message = "Dados da instalação são obrigatórios")
        InstalacaoAlugavelBaseDTO base,

        @Min(1)
        @NotNull(message = "Informar somente números inteiros, mínimo 1 hora")
        int horas
) implements InstalacaoAlugavelDTO {

    @Override public String nome() { return base.nome(); }
    @Override public BigDecimal precoBase() { return base.precoBase(); }
    @Override public Boolean isDisponivel() { return base.isDisponivel(); }
    @Override public String descricao() { return base.descricao(); }
    @Override public Long id_hotel() { return base.id_hotel(); }
}