package com.pi.senac.Hotel4ma.controller.old;

import com.pi.senac.Hotel4ma.dtos.Funcionario.Response.FuncionarioResponseDTO;
import com.pi.senac.Hotel4ma.dtos.Hotel.Request.HotelRequestDTO;
import com.pi.senac.Hotel4ma.dtos.Hotel.Response.HotelResponseDTO;
import com.pi.senac.Hotel4ma.model.Hotel;
import com.pi.senac.Hotel4ma.service.HotelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hotel")
public class HotelController implements GenericController{

    private final HotelService service;
    private static String base_path = "/api/hotel";


    @PostMapping
    public ResponseEntity<Void> createHotel(@RequestBody @Valid HotelRequestDTO dto){
       HotelResponseDTO response = service.saveHotel(dto);
       URI location =  gerarHeaderLocation(base_path, response.id());
       return ResponseEntity.created(location).build();
    }


    @GetMapping("{id}")
    public ResponseEntity<HotelResponseDTO> findById(@PathVariable("id") Long id) {
        var hotel = service.findById(id);
        if (hotel == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(hotel);
    }


    @GetMapping
    public ResponseEntity<List<HotelResponseDTO>> findAll() {
        List<HotelResponseDTO> hotels = service.findAll();
        return ResponseEntity.ok(hotels);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<HotelResponseDTO> updateHotel(@PathVariable Long id, @RequestBody @Valid HotelRequestDTO dto) {
//        HotelResponseDTO updatedHotel = service.updateHotel(id, dto);
//        return ResponseEntity.ok(updatedHotel);
//    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateHotel(
            @PathVariable Long id,
            @RequestBody @Valid HotelRequestDTO dto) {

        service.update(id, dto);
        return ResponseEntity.ok().build();
    }

}
