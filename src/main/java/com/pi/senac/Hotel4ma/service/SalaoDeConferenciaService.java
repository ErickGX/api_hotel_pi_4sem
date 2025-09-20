package com.pi.senac.Hotel4ma.service;

import com.pi.senac.Hotel4ma.dtos.InstalacaoAlugavel.salaoDeConferencia.Request.SalaoRequestDTO;
import com.pi.senac.Hotel4ma.dtos.InstalacaoAlugavel.salaoDeConferencia.Response.SalaoCustoResponseDTO;
import com.pi.senac.Hotel4ma.dtos.InstalacaoAlugavel.salaoDeConferencia.Response.SalaoResponseDTO;
import com.pi.senac.Hotel4ma.exceptions.ResourceNotFoundException;
import com.pi.senac.Hotel4ma.mappers.SalaoDeConferenciaMapper;
import com.pi.senac.Hotel4ma.model.Hotel;
import com.pi.senac.Hotel4ma.model.SalaoDeConferencia;
import com.pi.senac.Hotel4ma.repository.HotelRepository;
import com.pi.senac.Hotel4ma.repository.SalaoDeConferenciaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SalaoDeConferenciaService {


    private final SalaoDeConferenciaRepository repository;
    private final HotelRepository hotelRepository;
    private final SalaoDeConferenciaMapper mapper;

    public SalaoResponseDTO cadastrar(SalaoRequestDTO request) {
        Hotel hotel = hotelRepository.findById(request.id_hotel())
                .orElseThrow(() -> new ResourceNotFoundException("Hotel não encontrado"));

        SalaoDeConferencia salao = mapper.toEntity(request);
        salao.setHotel(hotel);

        SalaoDeConferencia salvo = repository.save(salao);
        return mapper.toDTO(salvo);
    }

    public SalaoCustoResponseDTO cadastrarComCusto(SalaoRequestDTO request) {
        Hotel hotel = hotelRepository.findById(request.id_hotel())
                .orElseThrow(() -> new ResourceNotFoundException("Hotel não encontrado"));

        SalaoDeConferencia salao = mapper.toEntity(request);
        salao.setHotel(hotel);

        SalaoDeConferencia salvo = repository.save(salao);

        BigDecimal custo = salvo.calcularCustoTotal(request.horas());

        return new SalaoCustoResponseDTO(
                salvo.getId(),
                salvo.getNome(),
                request.horas(),
                custo
        );
    }

    public List<SalaoResponseDTO> listarTodos() {
        return mapper.toList(repository.findAll());
    }

    public SalaoResponseDTO buscarPorId(Long id) {
        SalaoDeConferencia salao = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Salão não encontrado"));
        return mapper.toDTO(salao);
    }

    public SalaoResponseDTO atualizar(Long id, SalaoRequestDTO request) {
        SalaoDeConferencia salao = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Salão não encontrado"));

        mapper.updateEntityFromDTO(request, salao);

        Hotel hotel = hotelRepository.findById(request.id_hotel())
                .orElseThrow(() -> new ResourceNotFoundException("Hotel não encontrado"));
        salao.setHotel(hotel);

        SalaoDeConferencia atualizado = repository.save(salao);
        return mapper.toDTO(atualizado);
    }


    public SalaoCustoResponseDTO calcularCusto(Long id, int horas) {
        SalaoDeConferencia salao = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Salão não encontrado"));

        BigDecimal custo = salao.calcularCustoTotal(horas);

        return new SalaoCustoResponseDTO(
                salao.getId(),
                salao.getNome(),
                horas,
                custo
        );
    }

    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Salão não encontrado");
        }
        repository.deleteById(id);
    }
}