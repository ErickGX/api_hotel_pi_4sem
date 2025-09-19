package com.pi.senac.Hotel4ma.dtos.Espacos.Request;

import com.pi.senac.Hotel4ma.enums.TipoEspacos;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record EspacosRequestDTO(


        @NotNull(message = "O tipo de espaço é obrigatório")
        TipoEspacos tipoEspacos,

        @NotBlank(message = "A descrição é obrigatória")
        @Size(min = 3, max = 255, message = "A descrição deve conter entre 3 e 255 caracteres")
        String descricao,

        @NotNull(message = "O ID do hotel é obrigatório")
        Long id_hotel

) {
}
