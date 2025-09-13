package com.pi.senac.Hotel4ma.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@MappedSuperclass
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class InstalacaoAlugavel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(name = "preco_base",precision = 10, scale = 2, nullable = false)
    private BigDecimal precoBase;

    @Column(name = "is_disponivel", nullable = false)
    private Boolean isDisponivel = true;

    @Column(nullable = false)
    private String descricao;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_hotel") //FK na tabela Espacos
    private Hotel hotel;

    //campo para subir a imagem do local sera feita futuramente

    //metodo auxiliar para calcular valor do quarto baseado no fator categoria , precobase e tempo
    protected abstract BigDecimal calcularCustoTotal(int multiplicador);

    //imagem sera adicionada mais a frente



}
