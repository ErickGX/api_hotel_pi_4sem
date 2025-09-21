package com.pi.senac.Hotel4ma.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class InstalacaoAlugavel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(name = "preco_base", precision = 10, scale = 2, nullable = false)
    private BigDecimal precoBase;

    @Column(name = "is_disponivel", nullable = false)
    private Boolean isDisponivel = true;

    @Column(nullable = false)
    private String descricao;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_hotel", nullable = false)
    private Hotel hotel;

    /**
     * Método auxiliar para cálculo padrão com fator e multiplicador.
     */
    protected BigDecimal calcularBaseComFator(BigDecimal fator, int multiplicador) {
        return precoBase.multiply(fator).multiply(BigDecimal.valueOf(multiplicador));
    }

    /**
     * Método abstrato que cada instalação deve implementar.
     */
    public abstract BigDecimal calcularCustoTotal(int multiplicador);
}