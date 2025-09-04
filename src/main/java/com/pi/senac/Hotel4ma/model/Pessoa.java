package com.pi.senac.Hotel4ma.model;


import com.pi.senac.Hotel4ma.validation.Email;
import com.pi.senac.Hotel4ma.validation.Senha;
import jakarta.persistence.Embedded;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Column;
import lombok.Data;

@Data
@MappedSuperclass
public abstract class Pessoa {

    @Column(nullable = false, length = 100)
    protected String nome;

    @Embedded
    protected Email email;

    @Embedded
    protected Senha senha;

    @Column(length = 20)
    protected String telefone;
}
