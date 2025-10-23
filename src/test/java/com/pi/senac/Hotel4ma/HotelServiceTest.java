package com.pi.senac.Hotel4ma;

import com.pi.senac.Hotel4ma.dtos.Hotel.Request.HotelRequestDTO;
import com.pi.senac.Hotel4ma.dtos.Hotel.Response.HotelResponseDTO;
import com.pi.senac.Hotel4ma.exceptions.ResourceNotFoundException;
import com.pi.senac.Hotel4ma.mappers.HotelMapper;
import com.pi.senac.Hotel4ma.model.Hotel;
import com.pi.senac.Hotel4ma.repository.HotelRepository;
import com.pi.senac.Hotel4ma.service.HotelService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HotelServiceTest {

    @InjectMocks
    private HotelService hotelService;

    @Mock
    private HotelRepository hotelRepository;

    @Mock
    private HotelMapper hotelMapper;

    private Hotel hotel;
    private HotelRequestDTO hotelRequestDTO;
    private HotelResponseDTO hotelResponseDTO;

    @BeforeEach
    void setUp() {
        hotel = new Hotel();
        hotel.setId(1L);
        hotel.setNome("Hotel Copacabana");

        hotelRequestDTO = new HotelRequestDTO("Hotel Copacabana");

        hotelResponseDTO = new HotelResponseDTO(
                1L,
                "Hotel Copacabana",
                new ArrayList<>(), // espacos
                new ArrayList<>()  // instalacaoAlugaveis
        );
    }

    @Test
    void testSaveHotel() {
        when(hotelMapper.toEntity(hotelRequestDTO)).thenReturn(hotel);
        when(hotelRepository.save(hotel)).thenReturn(hotel);

        Long id = hotelService.saveHotel(hotelRequestDTO);

        assertEquals(1L, id);
        verify(hotelRepository).save(hotel);
    }

    @Test
    void testFindDtoById() {
        when(hotelRepository.findById(1L)).thenReturn(Optional.of(hotel));
        when(hotelMapper.toDTO(hotel)).thenReturn(hotelResponseDTO);

        HotelResponseDTO result = hotelService.findDtoById(1L);

        assertEquals("Hotel Copacabana", result.nome());
    }

    @Test
    void testFindDtoById_NotFound() {
        when(hotelRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> hotelService.findDtoById(1L));
    }

    @Test
    void testGetHotelById() {
        when(hotelRepository.findById(1L)).thenReturn(Optional.of(hotel));

        Hotel result = hotelService.getHotelById(1L);

        assertEquals("Hotel Copacabana", result.getNome());
    }

    @Test
    void testGetHotelById_NotFound() {
        when(hotelRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> hotelService.getHotelById(1L));
    }

    @Test
    void testFindAll() {
        when(hotelRepository.findAll()).thenReturn(List.of(hotel));
        when(hotelMapper.toDTO(hotel)).thenReturn(hotelResponseDTO);

        List<HotelResponseDTO> result = hotelService.findAll();

        assertEquals(1, result.size());
        assertEquals("Hotel Copacabana", result.get(0).nome());
    }

    @Test
    void testUpdate() {
        when(hotelRepository.findById(1L)).thenReturn(Optional.of(hotel));
        doNothing().when(hotelMapper).updateEntityFromDTO(hotelRequestDTO, hotel);

        hotelService.update(1L, hotelRequestDTO);

        verify(hotelMapper).updateEntityFromDTO(hotelRequestDTO, hotel);
    }

    @Test
    void testDeleteById() {
        when(hotelRepository.existsById(1L)).thenReturn(true);
        doNothing().when(hotelRepository).deleteById(1L);

        hotelService.deleteById(1L);

        verify(hotelRepository).deleteById(1L);
    }

    @Test
    void testDeleteById_NotFound() {
        when(hotelRepository.existsById(1L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> hotelService.deleteById(1L));
    }
}