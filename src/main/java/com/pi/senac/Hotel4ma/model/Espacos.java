package com.pi.senac.Hotel4ma.model;

import com.pi.senac.Hotel4ma.enums.TipoEspacos;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Espacos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoEspacos  tipoEspacos;

    @Column(nullable = false)
    private String descricao;

    //implementacao da foto do espaco - futuramente

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_hotel") //FK na tabela Espacos
    private Hotel hotel; //cada espaco pertence a um Hotel



}
