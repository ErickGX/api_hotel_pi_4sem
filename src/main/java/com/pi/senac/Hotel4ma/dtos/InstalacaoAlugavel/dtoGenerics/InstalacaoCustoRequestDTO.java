package com.pi.senac.Hotel4ma.dtos.InstalacaoAlugavel.dtoGenerics;

import com.pi.senac.Hotel4ma.enums.TipoSalaConferencia;
import com.pi.senac.Hotel4ma.enums.TipoSpa;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record InstalacaoCustoRequestDTO(

        @NotNull(message = "Tipo da sala é obrigatório")
        TipoSalaConferencia tipoSalaConferencia,

        @Valid
        InstalacaoAlugavelBaseDTO base,

        @Min(1)
        @NotNull(message = "Informar somente números inteiros, mínimo 1 hora")
        int horas,

        TipoSpa tipoSpa

) implements InstalacaoAlugavelDTO {

        @Override public String nome() { return base.nome(); }
        @Override public BigDecimal precoBase() { return base.precoBase(); }
        @Override public Boolean isDisponivel() { return base.isDisponivel(); }
        @Override public String descricao() { return base.descricao(); }
        @Override public Long id_hotel() { return base.id_hotel(); }
}


