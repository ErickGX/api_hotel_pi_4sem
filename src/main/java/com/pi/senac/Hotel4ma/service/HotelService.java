package com.pi.senac.Hotel4ma.service;

import com.pi.senac.Hotel4ma.dtos.Hotel.Request.HotelRequestDTO;
import com.pi.senac.Hotel4ma.dtos.Hotel.Response.HotelResponseDTO;
import com.pi.senac.Hotel4ma.exceptions.ResourceNotFoundException;
import com.pi.senac.Hotel4ma.mappers.HotelMapper;
import com.pi.senac.Hotel4ma.model.Hotel;
import com.pi.senac.Hotel4ma.repository.HotelRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HotelService {

    private final HotelRepository repository;
    private final HotelMapper mapper;

    // Salvar novo hotel
    @Transactional
    public Long saveHotel(HotelRequestDTO dto) {
      return repository.save(mapper.toEntity(dto)).getId();
    }

    /**
     * Busca um hotel pelo ID e o converte para um DTO de resposta.
     * Ideal para ser usado pela camada de Controller.
     */
    @Transactional(readOnly = true)
    public HotelResponseDTO findDtoById(Long id) {
        Hotel hotel = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel não encontrado com o ID: " + id));
        return mapper.toDTO(hotel);
    }

    /**
     * Busca a entidade Hotel pelo ID.
     * Ideal para ser usado por outros serviços que precisam do objeto de domínio.
     * O nome "get" indica que ele lança uma exceção se o recurso não for encontrado.
     */
    //Metodo auxiliar para outros servicos
    @Transactional(readOnly = true)
    public Hotel getHotelById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel não encontrado com o ID: " + id));
    }


    @Transactional(readOnly = true)
    public List<HotelResponseDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }


    // Atualiza um hotel existente com base no ID e nos dados do DTO
    @Transactional
    public void update(Long id, HotelRequestDTO dto) {
        Hotel existingHotel = getHotelById(id);

        // Atualiza os campos do hotel existente com os dados do DTO
        mapper.updateEntityFromDTO(dto, existingHotel);

    }

    @Transactional
    public void deleteById(Long id) {
        // Verifica se o recurso existe.
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Recurso não encontrado com o ID: " + id);
        }
        // Passo 2: Se existe, manda deletar.
        repository.deleteById(id);
    }


}