package com.pi.senac.Hotel4ma.service;

import com.pi.senac.Hotel4ma.model.Cliente;
import com.pi.senac.Hotel4ma.model.Funcionario;
import com.pi.senac.Hotel4ma.model.ReservaSala;
import com.pi.senac.Hotel4ma.model.Sala;
import com.pi.senac.Hotel4ma.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class ReservaSalaService {

    private final ReservaSalaRepository reservaSalaRepository;
    private final SalaRepository salaRepository;
    private final ClienteRepository clienteRepository;
    private final FuncionarioRepository funcionarioRepository;

    @Autowired
    public ReservaSalaService(
            ReservaSalaRepository reservaSalaRepository,
            SalaRepository salaRepository,
            ClienteRepository clienteRepository,
            FuncionarioRepository funcionarioRepository
    ) {
        this.reservaSalaRepository = reservaSalaRepository;
        this.salaRepository = salaRepository;
        this.clienteRepository = clienteRepository;
        this.funcionarioRepository = funcionarioRepository;
    }

    public List<ReservaSala> listarTodos() {
        return (List<ReservaSala>) reservaSalaRepository.findAll();
    }

    public ReservaSala buscarPorId(Long id) {
        return reservaSalaRepository.findById(id).orElse(null);
    }

    public boolean verificarDisponibilidade(ReservaSala reserva) {
        List<ReservaSala> conflitos = reservaSalaRepository.findReservasConflitantes(
                reserva.getSala().getIdSala(),
                reserva.getDataReserva(),
                reserva.getHoraInicio(),
                reserva.getHoraFim()
        );

        return conflitos.isEmpty() ||
                (conflitos.size() == 1 && conflitos.get(0).getId().equals(reserva.getId()));
    }

    public ReservaSala salvar(ReservaSala reservaSala) {
        carregarEntidades(reservaSala);
        calcularValorTotal(reservaSala);
        return reservaSalaRepository.save(reservaSala);
    }

    public ReservaSala atualizar(Long id, ReservaSala reservaAtualizada) {
        return reservaSalaRepository.findById(id).map(reserva -> {
            reserva.setDataReserva(reservaAtualizada.getDataReserva());
            reserva.setHoraInicio(reservaAtualizada.getHoraInicio());
            reserva.setHoraFim(reservaAtualizada.getHoraFim());
            reserva.setStatus(reservaAtualizada.getStatus());

            reserva.setSala(reservaAtualizada.getSala());
            reserva.setCliente(reservaAtualizada.getCliente());
            reserva.setFuncionario(reservaAtualizada.getFuncionario());

            carregarEntidades(reserva);
            calcularValorTotal(reserva);

            return reservaSalaRepository.save(reserva);
        }).orElse(null);
    }

    public void deletar(Long id) {
        reservaSalaRepository.deleteById(id);
    }

    // Métodos auxiliares

    private void carregarEntidades(ReservaSala reserva) {
        if (reserva.getSala() != null && reserva.getSala().getIdSala() != null) {
            Sala salaCompleta = salaRepository.findById(reserva.getSala().getIdSala())
                    .orElseThrow(() -> new RuntimeException("Sala não encontrada"));
            reserva.setSala(salaCompleta);
        }

        if (reserva.getFuncionario() != null && reserva.getFuncionario().getId() != null) {
            Funcionario funcionarioCompleto = funcionarioRepository.findById(reserva.getFuncionario().getId())
                    .orElseThrow(() -> new RuntimeException("Funcionário não encontrado"));
            reserva.setFuncionario(funcionarioCompleto);
        }

        if (reserva.getCliente() != null && reserva.getCliente().getId() != null) {
            Cliente clienteCompleto = clienteRepository.findById(reserva.getCliente().getId())
                    .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
            reserva.setCliente(clienteCompleto);
        }
    }

    private void calcularValorTotal(ReservaSala reserva) {
        if (reserva.getSala() != null && reserva.getSala().getValorHora() != null &&
                reserva.getHoraInicio() != null && reserva.getHoraFim() != null) {

            long horas = ChronoUnit.HOURS.between(reserva.getHoraInicio(), reserva.getHoraFim());
            if (horas <= 0) horas = 1;
            reserva.setValorTotal(reserva.getSala().getValorHora() * horas);
        } else {
            reserva.setValorTotal(0.0);
        }
    }
}