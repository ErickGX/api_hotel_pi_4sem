package com.pi.senac.Hotel4ma.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@EntityListeners(AuditingEntityListener.class)
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


    @CreatedDate //Spring preenche a data automaticamente ao criar
    @Column(name = "data_cadastro", nullable = false)
    private LocalDateTime dataCadastro;

    @LastModifiedDate //Spring preenche a data automaticamente ao atualizar
    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    // Multiplicador: diárias, horas, etc.
    public BigDecimal calcularCustoTotal(int multiplicador) {
        return calcularCusto(precoBase, getFator(), multiplicador);
    }

    // Cada subclasse define apenas seu fator
    protected abstract double getFator();

    // Metodo centralizado de cálculo
    private BigDecimal calcularCusto(BigDecimal precoBase, double fator, int multiplicador) {
        return precoBase.multiply(BigDecimal.valueOf(fator))
                .multiply(BigDecimal.valueOf(multiplicador));
    }
}