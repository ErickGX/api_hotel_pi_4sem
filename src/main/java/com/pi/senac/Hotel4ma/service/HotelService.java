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

    // Buscar hotel por ID
    @Transactional(readOnly = true)
    public HotelResponseDTO findById(Long id) {
        Hotel hotel = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hotel não encontrado com ID: " + id));
        return mapper.toDTO(hotel);
    }

    // Listar todos os hotéis
    @Transactional(readOnly = true)
    public List<HotelResponseDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }


    @Transactional
    public HotelResponseDTO update(Long id, HotelRequestDTO dto) {
        Hotel existingHotel = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel não encontrado com o ID: " + id));

        // Atualiza os campos do hotel existente com os dados do DTO
        mapper.updateEntityFromDTO(dto, existingHotel);

        Hotel updatedHotel = repository.save(existingHotel);
        return mapper.toDTO(updatedHotel);
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