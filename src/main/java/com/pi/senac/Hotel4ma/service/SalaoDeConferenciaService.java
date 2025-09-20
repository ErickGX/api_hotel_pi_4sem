package com.pi.senac.Hotel4ma.service;

import com.pi.senac.Hotel4ma.dtos.InstalacaoAlugavel.salaoDeConferencia.Request.SalaoRequestDTO;
import com.pi.senac.Hotel4ma.dtos.InstalacaoAlugavel.salaoDeConferencia.Response.SalaoCustoResponseDTO;
import com.pi.senac.Hotel4ma.dtos.InstalacaoAlugavel.salaoDeConferencia.Response.SalaoResponseDTO;
import com.pi.senac.Hotel4ma.mappers.SalaoDeConferenciaMapper;
import com.pi.senac.Hotel4ma.model.Hotel;
import com.pi.senac.Hotel4ma.model.SalaoDeConferencia;
import com.pi.senac.Hotel4ma.repository.HotelRepository;
import com.pi.senac.Hotel4ma.repository.SalaoDeConferenciaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class SalaoDeConferenciaService {

    @Autowired
    private SalaoDeConferenciaRepository salaoRepository;

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private SalaoDeConferenciaMapper mapper;

    public SalaoResponseDTO cadastrar(SalaoRequestDTO request) {
        Hotel hotel = hotelRepository.findById(request.id_hotel())
                .orElseThrow(() -> new EntityNotFoundException("Hotel não encontrado"));

        SalaoDeConferencia salao = mapper.toEntity(request);
        salao.setHotel(hotel);

        SalaoDeConferencia salvo = salaoRepository.save(salao);
        return mapper.toDTO(salvo);
    }

    public SalaoCustoResponseDTO cadastrarComCusto(SalaoRequestDTO request) {
        Hotel hotel = hotelRepository.findById(request.id_hotel())
                .orElseThrow(() -> new EntityNotFoundException("Hotel não encontrado"));

        SalaoDeConferencia salao = mapper.toEntity(request);
        salao.setHotel(hotel);

        SalaoDeConferencia salvo = salaoRepository.save(salao);

        BigDecimal custo = salvo.calcularCustoTotal(request.horas());

        return new SalaoCustoResponseDTO(
                salvo.getId(),
                salvo.getNome(),
                request.horas(),
                custo
        );
    }

    public List<SalaoResponseDTO> listarTodos() {
        return mapper.toList(salaoRepository.findAll());
    }

    public SalaoResponseDTO buscarPorId(Long id) {
        SalaoDeConferencia salao = salaoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Salão não encontrado"));
        return mapper.toDTO(salao);
    }

    public SalaoResponseDTO atualizar(Long id, SalaoRequestDTO request) {
        SalaoDeConferencia salao = salaoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Salão não encontrado"));

        mapper.updateEntityFromDTO(request, salao);

        Hotel hotel = hotelRepository.findById(request.id_hotel())
                .orElseThrow(() -> new EntityNotFoundException("Hotel não encontrado"));
        salao.setHotel(hotel);

        SalaoDeConferencia atualizado = salaoRepository.save(salao);
        return mapper.toDTO(atualizado);
    }


    public SalaoCustoResponseDTO calcularCusto(Long id, int horas) {
        SalaoDeConferencia salao = salaoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Salão não encontrado"));

        BigDecimal custo = salao.calcularCustoTotal(horas);

        return new SalaoCustoResponseDTO(
                salao.getId(),
                salao.getNome(),
                horas,
                custo
        );
    }

    public void deletar(Long id) {
        if (!salaoRepository.existsById(id)) {
            throw new EntityNotFoundException("Salão não encontrado");
        }
        salaoRepository.deleteById(id);
    }
}