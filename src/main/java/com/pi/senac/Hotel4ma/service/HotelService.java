package com.pi.senac.Hotel4ma.service;

import com.pi.senac.Hotel4ma.model.Hotel;
import com.pi.senac.Hotel4ma.repository.HotelRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelService {
    private final HotelRepository hotelRepository;

    public HotelService(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    public List<Hotel> listarTodos() {
        return hotelRepository.findAll();
    }

    public Hotel buscarPorId(Long id) {
        return hotelRepository.findById(id).orElse(null);
    }

    public Hotel salvar(Hotel hotel) {
        if (hotel.getQuartos() == null || hotel.getQuartos().isEmpty()) {
            throw new IllegalArgumentException("O hotel deve possuir pelo menos um quarto.");
            //Para criar um hotel é obrigatorio ter 1 quarto ? N
        }

        if (hotel.getFuncionarios() != null) {
            hotel.getFuncionarios().forEach(funcionario -> funcionario.setHotel(hotel));
        }

        hotel.getQuartos().forEach(quarto -> quarto.setHotel(hotel));

        if (hotel.getSalas() != null) {
            hotel.getSalas().forEach(sala -> sala.setHotel(hotel));
        }

        return hotelRepository.save(hotel);
    }

    public Hotel atualizar(Long id, Hotel hotelAtualizado) {
        return hotelRepository.findById(id).map(hotel -> {
            hotel.setNome(hotelAtualizado.getNome());
            hotel.setEndereco(hotelAtualizado.getEndereco());
            hotel.setTelefone(hotelAtualizado.getTelefone());
            hotel.setEmail(hotelAtualizado.getEmail());

            hotel.getFuncionarios().clear();
            if (hotelAtualizado.getFuncionarios() != null) {
                hotelAtualizado.getFuncionarios().forEach(funcionario -> {
                    funcionario.setHotel(hotel);
                    hotel.getFuncionarios().add(funcionario);
                });
            }

            hotel.getQuartos().clear();
            if (hotelAtualizado.getQuartos() == null || hotelAtualizado.getQuartos().isEmpty()) {
                throw new IllegalArgumentException("O hotel deve possuir pelo menos um quarto.");
            }
            hotelAtualizado.getQuartos().forEach(quarto -> {
                quarto.setHotel(hotel);
                hotel.getQuartos().add(quarto);
            });

            hotel.getSalas().clear();
            if (hotelAtualizado.getSalas() != null) {
                hotelAtualizado.getSalas().forEach(sala -> {
                    sala.setHotel(hotel);
                    hotel.getSalas().add(sala);
                });
            }

            return hotelRepository.save(hotel);
        }).orElse(null);
    }

    public void deletar(Long id) {
        hotelRepository.deleteById(id);
    }
}
