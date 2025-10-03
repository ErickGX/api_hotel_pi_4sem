package com.pi.senac.Hotel4ma.service;


import com.pi.senac.Hotel4ma.dtos.Reserva.Request.ReservaRequest;
import com.pi.senac.Hotel4ma.exceptions.ResourceNotFoundException;
import com.pi.senac.Hotel4ma.model.*;
import com.pi.senac.Hotel4ma.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservaService {

    private final ReservaRepository reservaRepository;
    private final ClienteRepository clienteRepository;
    private final InstalacaoRepository instalacaoRepository;
    private final PagamentoRepository pagamentoRepository;
    private final FuncionarioRepository funcionarioRepository;




    public Reserva save(ReservaRequest dto) {
        Funcionario funcionario = null;

        if (dto.checkIn().isAfter(dto.checkOut())){
            throw new IllegalArgumentException("Data de check-in não pode ser após o check-out.");
        }

        if (dto.funcionarioId() != null){
            funcionario = funcionarioRepository.findById(dto.funcionarioId())
                    .orElseThrow(() -> new ResourceNotFoundException("Funcionario não encontrado com o ID: " + dto.funcionarioId()));
        }

        Cliente cliente = clienteRepository.findById(dto.clienteId())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com o ID: " + dto.clienteId()));

        InstalacaoAlugavel instalacao = instalacaoRepository.findById(dto.instalacaoAlugavelId())
                .orElseThrow(() -> new ResourceNotFoundException("Instalação não encontrada com o ID: " + dto.instalacaoAlugavelId()));



        int dias = (int) ChronoUnit.DAYS.between(dto.checkIn(), dto.checkOut());

        BigDecimal custoTotal = instalacao.calcularCustoTotal(dias);

        Reserva reserva = new Reserva();
        reserva.setTipoPagamento(dto.tipoPagamento());
        reserva.setValorTotal(custoTotal);
        reserva.setStatus(dto.statusReserva());
        reserva.setCheckIn(dto.checkIn());
        reserva.setCheckOut(dto.checkOut());
        reserva.setCliente(cliente);
        reserva.setInstalacaoAlugavel(instalacao);

        if (funcionario != null) {
            reserva.setFuncionario(funcionario);
        }


        Reserva reservaSalva  =   reservaRepository.save(reserva);

        Pagamento pagamento = new Pagamento();
        pagamento.setTipoPagamento(reserva.getTipoPagamento());
        pagamento.setValor(reserva.getValorTotal());
        pagamento.setHoraPagamento(reserva.getDataCadastro());
        pagamento.setCliente(reserva.getCliente());
        pagamento.setReserva(reservaSalva);

        Pagamento pagamentoSalva  =   pagamentoRepository.save(pagamento);

        reservaSalva.setPagamento(pagamentoSalva);

        return reservaSalva;
    }

    public List<Reserva> findAll() {

        return reservaRepository.findAll();
    }
}
