package com.pi.senac.Hotel4ma.service;

import com.pi.senac.Hotel4ma.model.Quarto;
import com.pi.senac.Hotel4ma.repository.QuartoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuartoService {

    private final QuartoRepository quartoRepository;

    public QuartoService(QuartoRepository quartoRepository) {
        this.quartoRepository = quartoRepository;
    }
//
//    public List<Quarto> listarTodos() {
//        return quartoRepository.findAll();
//    }
//
//    public Quarto buscarPorId(Long id) {
//        return quartoRepository.findById(id).orElse(null);
//    }
//
//    public Quarto salvar(Quarto quarto) {
//        if (quarto.getHotel() == null) {
//            throw new IllegalArgumentException("O quarto deve estar associado a um hotel.");
//        }
//        return quartoRepository.save(quarto);
//    }
//
//    public Quarto atualizar(Long id, Quarto quartoAtualizado) {
//        return quartoRepository.findById(id).map(quarto -> {
//            quarto.setNumero(quartoAtualizado.getNumero());
//            quarto.setTipo(quartoAtualizado.getTipo());
//            quarto.setDescricao(quartoAtualizado.getDescricao());
//            quarto.setValorDiaria(quartoAtualizado.getValorDiaria());
//            quarto.setStatus(quartoAtualizado.getStatus());
//
//            if (quartoAtualizado.getHotel() == null) {
//                throw new IllegalArgumentException("O quarto deve estar associado a um hotel.");
//            }
//            quarto.setHotel(quartoAtualizado.getHotel());
//
//            if (quarto.getReservasHospedagem() != null) {
//                quarto.getReservasHospedagem().clear();
//            }
//            if (quartoAtualizado.getReservasHospedagem() != null) {
//                quartoAtualizado.getReservasHospedagem().forEach(reserva -> {
//                    reserva.setQuarto(quarto);
//                    quarto.getReservasHospedagem().add(reserva);
//                });
//            }
//
//            return quartoRepository.save(quarto);
//        }).orElse(null);
//    }
//
//    public void deletar(Long id) {
//        quartoRepository.deleteById(id);
//    }
}
