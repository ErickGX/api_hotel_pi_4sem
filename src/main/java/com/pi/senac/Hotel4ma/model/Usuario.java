package com.pi.senac.Hotel4ma.model;


import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
//config necessaria para criar um listener para os @CreatedDate e  @LastModifiedDate
//ativar config @EnableJpaAuditing na classe de configuração ex : Application.Java ou outra
public abstract class Usuario {

    @Column(nullable = false, length = 100)
    private String nome;

    // ver se o uso de @Email vai compensar pela complexidade dessas classes de validation manuais
    //Por algum motivo email nao ta indo pro banco de dados , verificar depois
    //@Embedded
    //protected Email email;

    @Email
    @Column(unique = true)
    private String email;


//    @Embedded
//    private Senha senha;
    @Column(nullable = false, length = 100)
    private String senha;

    @Pattern(regexp = "^[0-9]{11}$", message = "Telefone deve conter exatamente 11 dígitos numéricos")
    @Size(min = 11, max = 11, message = "Telefone deve ter exatamente 11 dígitos")
    @Column(length = 11)
    private String telefone;


    @CreatedDate //Spring preenche a data automaticamente ao criar
    @Column(name = "data_cadastro", nullable = false)
    private LocalDateTime dataCadastro;

    @LastModifiedDate //Spring preenche a data automaticamente ao atualizar
    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;
}
