package com.pi.senac.Hotel4ma.service;

import com.pi.senac.Hotel4ma.repository.ClienteRepository;
import com.pi.senac.Hotel4ma.repository.FuncionarioRepository;
import com.pi.senac.Hotel4ma.repository.QuartoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservaHospedagemService {

    private final QuartoRepository quartoRepository;
    private final ClienteRepository clienteRepository;
    private final FuncionarioRepository funcionarioRepository;


    @Autowired
    public ReservaHospedagemService(
            QuartoRepository quartoRepository,
            ClienteRepository clienteRepository,
            FuncionarioRepository funcionarioRepository
    ) {
        this.quartoRepository = quartoRepository;
        this.clienteRepository = clienteRepository;
        this.funcionarioRepository = funcionarioRepository;
    }
//
//    public List<ReservaHospedagem> listarTodos() {
//        return (List<ReservaHospedagem>) reservaHospedagemRepository.findAll();
//    }
//
//    public ReservaHospedagem buscarPorId(Long id) {
//        return reservaHospedagemRepository.findById(id).orElse(null);
//    }
//
//    public ReservaHospedagem salvar(ReservaHospedagem reserva) {
//        // Carrega o quarto completo
//        Quarto quartoCompleto = quartoRepository.findById(reserva.getQuarto().getIdQuarto())
//                .orElseThrow(() -> new RuntimeException("Quarto não encontrado"));
//        reserva.setQuarto(quartoCompleto);
//
//        // Carrega o recepcionista completo (opcional, se necessário)
//        Funcionario funcionarioCompleto = funcionarioRepository.findById(reserva.getFuncionario().getId())
//                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado"));
//        reserva.setFuncionario(funcionarioCompleto);
//
//        // Carrega o cliente completo (opcional, se necessário)
//        Cliente clienteCompleto = clienteRepository.findById(reserva.getCliente().getId())
//                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
//        reserva.setCliente(clienteCompleto);
//
//        // Valida disponibilidade
//        if (!verificarDisponibilidade(reserva)) {
//            throw new IllegalArgumentException("Quarto já está reservado nesse período.");
//        }
//
//        // Vincula pagamento, se houver
//        if (reserva.getPagamento() != null) {
//            reserva.getPagamento().setReservaHospedagem(reserva);
//        }
//
//        return reservaHospedagemRepository.save(reserva);
//    }
//
//
//
//
//
//
//
//
//
//
//
//
//
//    public ReservaHospedagem atualizar(Long id, ReservaHospedagem reservaAtualizada) {
//        return reservaHospedagemRepository.findById(id).map(reserva -> {
//            // Atualiza dados principais
//            reserva.setCliente(reservaAtualizada.getCliente());
//            reserva.setFuncionario(reservaAtualizada.getFuncionario());
//            reserva.setQuarto(reservaAtualizada.getQuarto());
//            reserva.setDataCheckin(reservaAtualizada.getDataCheckin());
//            reserva.setDataCheckout(reservaAtualizada.getDataCheckout());
//            reserva.setStatus(reservaAtualizada.getStatus());
//            //reserva.setValorTotal(reservaAtualizada.getValorTotal());
//
//            // Atualiza pagamento mantendo vínculo bidirecional
//            if (reservaAtualizada.getPagamento() != null) {
//                reservaAtualizada.getPagamento().setReservaHospedagem(reserva);
//                reserva.setPagamento(reservaAtualizada.getPagamento());
//            } else {
//                reserva.setPagamento(null);
//            }
//
//            return reservaHospedagemRepository.save(reserva);
//        }).orElse(null);
//    }
//
//    public void deletar(Long id) {
//        reservaHospedagemRepository.deleteById(id);
//    }
//
//    public boolean verificarDisponibilidade(ReservaHospedagem novaReserva) {
//        List<ReservaHospedagem> conflitos =
//                reservaHospedagemRepository.findReservasConflitantes(
//                        novaReserva.getQuarto().getIdQuarto(),
//                        novaReserva.getDataCheckin(),
//                        novaReserva.getDataCheckout()
//                );
//
//        return conflitos.isEmpty(); // se não tiver conflito, está disponível
//    }

}

