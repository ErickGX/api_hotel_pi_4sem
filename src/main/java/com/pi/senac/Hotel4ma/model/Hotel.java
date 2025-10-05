package com.pi.senac.Hotel4ma.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Funcionario> funcionarios; //Relação forte

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, orphanRemoval = true )
    private List<Espacos> espacos; //Relação forte

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InstalacaoAlugavel> instalacaoAlugaveis = new ArrayList<>(); //Relação Forte


}