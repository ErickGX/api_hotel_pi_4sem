package com.pi.senac.Hotel4ma.controller.old;

import com.pi.senac.Hotel4ma.dtos.Hotel.Request.HotelRequestDTO;
import com.pi.senac.Hotel4ma.dtos.Hotel.Response.HotelResponseDTO;
import com.pi.senac.Hotel4ma.model.Hotel;
import com.pi.senac.Hotel4ma.service.HotelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

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



}
