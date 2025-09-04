package com.pi.senac.Hotel4ma.service;

import com.pi.senac.Hotel4ma.businessRule.PagamentoDescAVista;
import com.pi.senac.Hotel4ma.model.Pagamento;
import com.pi.senac.Hotel4ma.model.ReservaHospedagem;
import com.pi.senac.Hotel4ma.model.ReservaSala;
import com.pi.senac.Hotel4ma.repository.PagamentoRepository;
import com.pi.senac.Hotel4ma.repository.ReservaHospedagemRepository;
import com.pi.senac.Hotel4ma.repository.ReservaSalaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PagamentoService {
    private final PagamentoRepository pagamentoRepository;
    private final PagamentoDescAVista pagamentoDescAVista;
    private final ReservaSalaRepository reservaSalaRepository;
    private final ReservaHospedagemRepository reservaHospedagemRepository;

    public PagamentoService(PagamentoRepository pagamentoRepository,
                            PagamentoDescAVista pagamentoDescAVista,
                            ReservaSalaRepository reservaSalaRepository,
                            ReservaHospedagemRepository reservaHospedagemRepository) {
        this.pagamentoRepository = pagamentoRepository;
        this.pagamentoDescAVista = pagamentoDescAVista;
        this.reservaSalaRepository = reservaSalaRepository;
        this.reservaHospedagemRepository = reservaHospedagemRepository;
    }

    public List<Pagamento> listarTodos() {
        return (List<Pagamento>) pagamentoRepository.findAll();
    }

    public Pagamento buscarPorId(Long id) {
        return pagamentoRepository.findById(id).orElse(null);
    }

    public Pagamento salvar(Pagamento pagamento) {
        if (pagamento.getReservaSala() != null) {
            Long idReservaSala = pagamento.getReservaSala().getId();
            ReservaSala reservaSala = reservaSalaRepository.findById(idReservaSala)
                    .orElseThrow(() -> new RuntimeException("Reserva de sala não encontrada"));
            pagamento.setReservaSala(reservaSala);
        }

        // Se estiver usando reservaHospedagem, faça o mesmo:
        if (pagamento.getReservaHospedagem() != null) {
            Long idReservaHospedagem = pagamento.getReservaHospedagem().getId();
            ReservaHospedagem reservaHospedagem = reservaHospedagemRepository.findById(idReservaHospedagem)
                    .orElseThrow(() -> new RuntimeException("Reserva de hospedagem não encontrada"));
            pagamento.setReservaHospedagem(reservaHospedagem);
        }

        return pagamentoRepository.save(pagamento);
    }



    public Pagamento atualizar(Long id, Pagamento pagamentoAtualizado) {
        return pagamentoRepository.findById(id).map(pagamento -> {
            pagamento.setFormaPagamento(pagamentoAtualizado.getFormaPagamento());
            pagamento.setDataPagamento(pagamentoAtualizado.getDataPagamento());
            pagamento.setStatus(pagamentoAtualizado.getStatus());

            if (pagamentoAtualizado.getReservaSala() != null) {
                Long idReservaSala = pagamentoAtualizado.getReservaSala().getId();
                ReservaSala reservaSala = reservaSalaRepository.findById(idReservaSala)
                        .orElseThrow(() -> new RuntimeException("Reserva de sala não encontrada"));
                pagamento.setReservaSala(reservaSala);
            } else {
                pagamento.setReservaSala(null);
            }

            if (pagamentoAtualizado.getReservaHospedagem() != null) {
                Long idReservaHospedagem = pagamentoAtualizado.getReservaHospedagem().getId();
                ReservaHospedagem reservaHospedagem = reservaHospedagemRepository.findById(idReservaHospedagem)
                        .orElseThrow(() -> new RuntimeException("Reserva de hospedagem não encontrada"));
                pagamento.setReservaHospedagem(reservaHospedagem);
            } else {
                pagamento.setReservaHospedagem(null);
            }

            Double valorFinal = pagamentoDescAVista.calcularValorComDesconto(pagamentoAtualizado);
            pagamento.setValor(valorFinal);

            return pagamentoRepository.save(pagamento);
        }).orElse(null);
    }
    public void deletar(Long id) {
        pagamentoRepository.deleteById(id);
    }
}
