package com.pi.senac.Hotel4ma.model;

import com.pi.senac.Hotel4ma.enums.StatusReserva;
import com.pi.senac.Hotel4ma.enums.TipoPagamento;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
//mover para DTO futuramente
@EntityListeners(AuditingEntityListener.class)
@SQLDelete(sql = "UPDATE {h-schema}reserva SET ativo = false WHERE id = ?") // 1. Intercepta o DELETE
@SQLRestriction(value = "ativo = true") // 2. Garante que UPDATEs também respeitem o filtro
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoPagamento tipoPagamento;

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal valorTotal;

    @Column(nullable = false)
    private boolean ativo = true;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusReserva statusReserva = StatusReserva.ATIVA;

    @Column(nullable = false)
    private LocalDateTime checkIn;

    @Column(nullable = false)
    private LocalDateTime checkOut;

    @CreatedDate //Spring preenche a data automaticamente ao criar
    @Column(name = "data_cadastro", nullable = false)
    private LocalDateTime dataCadastro;


    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente; // sempre existe

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "funcionario_id")
    private Funcionario funcionario; // opcional

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "id_instalacao", nullable = false)
    private InstalacaoAlugavel instalacaoAlugavel;
    //futuramente se possivel implementar uma lista de espaços por reserva
    //revisar possibilidade com o vagner

    @OneToOne(mappedBy = "reserva", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Pagamento pagamento;


    //metodo para calcular valor total baseado nos espacos alugados ira para a service

    //Dica para futura refatoração
    //Quando quiser permitir reservas com múltiplos espaços:
    //1-Criar uma tabela intermediária (ReservaEspaco) que armazene:
        //Reserva
        //Espaço alugável
        //Datas específicas (check-in/check-out)
        //Valor individual do espaço
    //2-Atualizar o serviço para:
        //Validar disponibilidade por espaço
        //Calcular valor total somando todos os espaços
        //Permitir pagamento global ou por espaço
    //O DTO também precisará refletir a lista de espaços com datas individuais.

}
