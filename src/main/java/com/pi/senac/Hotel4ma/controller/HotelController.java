package com.pi.senac.Hotel4ma.controller;

import com.pi.senac.Hotel4ma.dtos.Hotel.Request.HotelRequestDTO;
import com.pi.senac.Hotel4ma.dtos.Hotel.Response.HotelResponseDTO;
import com.pi.senac.Hotel4ma.service.HotelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hotel")
public class HotelController implements GenericController {

    private final HotelService service;
    private static String base_path = "/api/hotel";


    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> createHotel(@RequestBody @Valid HotelRequestDTO dto){
       Long id_gerado = service.saveHotel(dto);
       URI location =  gerarHeaderLocation(base_path, id_gerado);
       return ResponseEntity.created(location).build();
    }


    @GetMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<HotelResponseDTO> findById(@PathVariable("id") Long id) {
        var hotel = service.findDtoById(id);
        if (hotel == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(hotel);
    }


    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<HotelResponseDTO>> findAll() {
        List<HotelResponseDTO> hotels = service.findAll();
        return ResponseEntity.ok(hotels);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }


    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> updateHotel(
            @PathVariable Long id,
            @RequestBody @Valid HotelRequestDTO dto) {

        service.update(id, dto);
        return ResponseEntity.noContent().build();
    }

}
