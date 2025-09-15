package com.pi.senac.Hotel4ma.model;

import com.pi.senac.Hotel4ma.enums.StatusReserva;
import com.pi.senac.Hotel4ma.enums.TipoPagamento;
import com.pi.senac.Hotel4ma.validation.CheckDates;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@CheckDates //mover para DTO futuramente
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TipoPagamento tipoPagamento;

    @Column(name = "valor_total",precision = 10, scale = 2, nullable = false)
    private BigDecimal valorTotal;

    @Enumerated(EnumType.STRING)
    private StatusReserva status;

    @Column(nullable = false)
    private LocalDate checkIn;

    @Column(nullable = false)
    private LocalDate checkOut;

    @ManyToOne(optional = false)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente; // sempre existe

    @ManyToOne(optional = true)
    @JoinColumn(name = "funcionario_id")
    private Funcionario funcionario; // opcional

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_instalacao", nullable = false)
    private InstalacaoAlugavel instalacaoAlugavel;
    //futuramente se possivel implementar uma lista de espaços por reserva
    //revisar possibilidade com o vagner

    @OneToOne(mappedBy = "reserva", cascade = CascadeType.ALL, orphanRemoval = true)
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
