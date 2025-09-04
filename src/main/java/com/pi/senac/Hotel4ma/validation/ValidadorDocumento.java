package com.pi.senac.Hotel4ma.validation;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

@Data
@MappedSuperclass
public abstract class ValidadorDocumento {

    @Column(nullable = false, unique = true, length = 14)
    protected String numero;

    public abstract boolean validarDoc();
}
