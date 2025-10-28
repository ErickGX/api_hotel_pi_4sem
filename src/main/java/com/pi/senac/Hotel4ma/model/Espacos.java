package com.pi.senac.Hotel4ma.model;

import com.pi.senac.Hotel4ma.enums.TipoEspacos;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@SQLDelete(sql = "UPDATE {h-schema}espacos SET ativo = false WHERE id = ?") // 1. Intercepta o DELETE
@SQLRestriction(value = "ativo = true") // 2. Garante que UPDATEs também respeitem o filtro
public class Espacos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoEspacos  tipoEspacos;

    @Column(nullable = false)
    private String descricao;

    @Column(nullable = false)
    private boolean ativo = true;

    //implementacao da foto do espaco - futuramente

    // Funcionário precisa obrigatoriamente estar vinculado a um hotel
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_hotel", nullable = false)
    private Hotel hotel;

    @CreatedDate //Spring preenche a data automaticamente ao criar
    @Column(name = "data_cadastro", nullable = false)
    private LocalDateTime dataCadastro;

    @LastModifiedDate //Spring preenche a data automaticamente ao atualizar
    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;


}
