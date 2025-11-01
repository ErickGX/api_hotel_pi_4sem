package com.pi.senac.Hotel4ma.service;


import com.pi.senac.Hotel4ma.dtos.Reserva.Request.ReservaRequest;
import com.pi.senac.Hotel4ma.dtos.Reserva.Response.ReservaResponseDTO;
import com.pi.senac.Hotel4ma.enums.StatusReserva;
import com.pi.senac.Hotel4ma.exceptions.ReservaDataConflitanteException;
import com.pi.senac.Hotel4ma.mappers.ReservaMapper;
import com.pi.senac.Hotel4ma.model.*;
import com.pi.senac.Hotel4ma.repository.*;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservaService {

    private final ReservaRepository repository;
    private final ClienteService clienteService;
    private final InstalacaoService instalacaoService;
    private final FuncionarioService funcionarioService;
    private final ReservaMapper mapper;

    @Transactional
    public ReservaResponseDTO save(ReservaRequest dto) {

        Funcionario funcionario = null;
        if (dto.funcionarioId() != null) {
            funcionario = funcionarioService.getFuncionarioByid(dto.funcionarioId());
        }

        Cliente cliente = clienteService.getClienteById(dto.clienteId());//metodo get expoe a entidade Cliente
        InstalacaoAlugavel instalacao = instalacaoService.getInstalacaoById(dto.instalacaoAlugavelId());

        //Verifica se existe conflito de reserva -- null é porque é um save, no update passaria o id da reserva
        verificarConflitoReserva(instalacao.getId(), dto.checkIn(), dto.checkOut(), null);

        //Monta a entidade reserva usando o mapper, e as entidades relacionadas
        Reserva reserva = mapper.toEntity(dto, cliente, funcionario, instalacao);

        //Calcula o valor total da reserva
        var valorTotal = calculoPorHorasOuDias(reserva);
        reserva.setValorTotal(valorTotal);

        // Cria o pagamento associado à reserva
        Pagamento pagamento = new Pagamento();
        pagamento.setReserva(reserva);// o pagamento referencia a reserva
        pagamento.setCliente(cliente);
        pagamento.setHoraPagamento(LocalDateTime.now());
        pagamento.setTipoPagamento(reserva.getTipoPagamento());
        pagamento.setValorTotal(valorTotal);
        // Vincula o pagamento à reserva
        reserva.setPagamento(pagamento);
        //salva tudo de uma vez só(graças ao cascade)
        Reserva savedReserva = repository.save(reserva);

        return mapper.toDto(savedReserva);
    }


    @Transactional(readOnly = true)
    public List<ReservaResponseDTO> findAll() {
        return mapper.toList(repository.findAll());
    }

    //util para checar conflitos de reserva no save e no Update
    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    protected void verificarConflitoReserva(
            Long instalacaoId,
            LocalDateTime checkIn,
            LocalDateTime checkOut,
            Long reservaId){
        boolean conflito = repository.existsReservaConflitante(instalacaoId, checkIn, checkOut, reservaId);

        if (conflito){

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

            String checkInFormatado = checkIn.format(formatter);
            String checkOutFormatado = checkOut.format(formatter);

            throw new ReservaDataConflitanteException("Conflito de reserva para a instalação selecionada no período entre : " + checkInFormatado + " e " + checkOutFormatado);
        }
    }

    @Transactional
    public void saveFinalizadas(List<Reserva> reservas) {
        repository.saveAll(reservas);
    }

    public List<Reserva> finalizarReservasAutomaticamente() {
        return repository.findReservasAtivasParaFinalizar(StatusReserva.ATIVA, LocalDateTime.now());
    }

        //Função para calcular o valor total da reserva baseado no tipo de instalação
    private BigDecimal calculoPorHorasOuDias(Reserva reserva){
        if (reserva.getInstalacaoAlugavel() instanceof Quarto){
            int dias = (int) ChronoUnit.DAYS.between(reserva.getCheckIn(), reserva.getCheckOut());
            return reserva.getInstalacaoAlugavel().calcularCustoTotal(dias);
        } else {
            int horas = (int) ChronoUnit.HOURS.between(reserva.getCheckIn(), reserva.getCheckOut());
            return reserva.getInstalacaoAlugavel().calcularCustoTotal(horas);
        }
    }


//  public class CalculadoraDeDiarias {
//
//    private static final int HORA_LIMITE_DA_DIARIA = 12; // 12h, por exemplo
//
//    public int calcularDiarias(LocalDateTime checkIn, LocalDateTime checkOut) {
//        int diasCompletos = (int) ChronoUnit.DAYS.between(checkIn, checkOut);
//
//        // Se o checkout for depois da hora limite, adiciona um dia
//        if (checkOut.getHour() > HORA_LIMITE_DA_DIARIA) {
//            diasCompletos++;
//        }
//
//        // Garante que o mínimo seja 1 dia, mesmo em estadias curtas
//        return Math.max(diasCompletos, 1);
//    }
//}

}
