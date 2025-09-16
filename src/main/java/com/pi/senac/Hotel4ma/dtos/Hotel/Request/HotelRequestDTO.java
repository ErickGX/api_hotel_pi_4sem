package com.pi.senac.Hotel4ma.dtos.Hotel.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record HotelRequestDTO(
        @Size(min = 3, max = 100, message = "Nome deve conter no mínimo 3 e no máximo 80 caracteres")
        @NotBlank(message = "Campo nome é obrigatório")
        String nome
) {

}
