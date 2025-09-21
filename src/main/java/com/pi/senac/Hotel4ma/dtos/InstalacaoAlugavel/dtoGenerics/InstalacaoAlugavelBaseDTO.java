package com.pi.senac.Hotel4ma.dtos.InstalacaoAlugavel.dtoGenerics;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record InstalacaoAlugavelBaseDTO(

        @Size(min = 3, max = 150, message = "Nome deve conter entre 3 e 150 caracteres")
        @NotBlank(message = "Campo nome é obrigatório")
        String nome,

        @NotNull(message = "Preço base é obrigatório")
        @DecimalMin(value = "0.00", inclusive = false, message = "Preço base deve ser maior que zero")
        BigDecimal precoBase,

        @NotNull(message = "Disponibilidade deve ser informada")
        Boolean isDisponivel,

        @Size(min = 10, max = 500, message = "Descrição deve conter entre 10 e 500 caracteres")
        @NotBlank(message = "Campo descrição é obrigatório")
        String descricao,

        @NotNull(message = "ID do hotel é obrigatório")
        Long id_hotel

) implements InstalacaoAlugavelDTO {}