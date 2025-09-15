package com.pi.senac.Hotel4ma.exceptions;

public record ApiValidationError(
        String campos,
        String messagem
) {
}
