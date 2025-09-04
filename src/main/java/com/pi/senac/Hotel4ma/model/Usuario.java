package com.pi.senac.Hotel4ma.model;


import com.pi.senac.Hotel4ma.validation.Senha;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public abstract class Usuario {

    @Column(nullable = false, length = 100)
    private String nome;

    // ver se o uso de @Email vai compensar pela complexidade dessas classes de validation manuais
    //Por algum motivo email nao ta indo pro banco de dados , verificar depois
//    @Embedded
//    protected Email email;

    @Email
    @Column(unique = true)
    private String email;


    @Embedded
    private Senha senha;

    @Column(length = 20)
    private String telefone;
}
