package com.pi.senac.Hotel4ma.service;

import com.pi.senac.Hotel4ma.model.Sala;
import com.pi.senac.Hotel4ma.repository.SalaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalaService {
    private final SalaRepository salaRepository;

    public SalaService(SalaRepository salaRepository) {
        this.salaRepository = salaRepository;
    }

    public List<Sala> listarTodos() {
        return (List<Sala>) salaRepository.findAll();
    }

    public Sala buscarPorId(Long id) {
        return salaRepository.findById(id).orElse(null);
    }

    public Sala salvar(Sala sala) {
        return salaRepository.save(sala);
    }

    // Atualizar sala existente
    public Sala atualizar(Long id, Sala salaAtualizada) {
        return salaRepository.findById(id).map(sala -> {
            // Atualiza os campos básicos
            sala.setNome(salaAtualizada.getNome());
            sala.setTipo(salaAtualizada.getTipo());
            sala.setCapacidade(salaAtualizada.getCapacidade());
            sala.setDescricao(salaAtualizada.getDescricao());
            sala.setValorHora(salaAtualizada.getValorHora());
            sala.setStatus(salaAtualizada.getStatus());

            // Atualiza o hotel vinculado
            sala.setHotel(salaAtualizada.getHotel());

            // Atualiza reservas de sala
            sala.getReservasSala().clear();
            if (salaAtualizada.getReservasSala() != null) {
                salaAtualizada.getReservasSala().forEach(reserva -> {
                    reserva.setSala(sala); // mantém o vínculo bidirecional
                    sala.getReservasSala().add(reserva);
                });
            }

            return salaRepository.save(sala);
        }).orElse(null);
    }

    public void deletar(Long id) {
        salaRepository.deleteById(id);
    }
}
