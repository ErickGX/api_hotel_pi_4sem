package com.pi.senac.Hotel4ma.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@SQLDelete(sql = "UPDATE {h-schema}hotel SET ativo = false WHERE id = ?") // 1. Intercepta o DELETE
@SQLRestriction(value = "ativo = true") // 2. Garante que UPDATEs também respeitem o filtro
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false)
    private boolean ativo = true;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Funcionario> funcionarios; //Relação forte

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, orphanRemoval = true )
    private List<Espacos> espacos; //Relação forte

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InstalacaoAlugavel> instalacaoAlugaveis = new ArrayList<>(); //Relação Forte


}