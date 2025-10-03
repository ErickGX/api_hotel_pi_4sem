package com.pi.senac.Hotel4ma.controller;


import com.pi.senac.Hotel4ma.dtos.Reserva.Request.ReservaRequest;
import com.pi.senac.Hotel4ma.model.Reserva;
import com.pi.senac.Hotel4ma.service.ReservaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reservas")
public class ReservaController implements GenericController{

    private final ReservaService service;

    private static String base_path = "/api/instalacoes";

    @PostMapping
    public ResponseEntity<Reserva> create(@RequestBody ReservaRequest dto){
        Reserva criada  =  service.save(dto);
        URI location =  gerarHeaderLocation(base_path, criada.getId());
        return ResponseEntity.created(location).build();
    }

    @GetMapping
    public ResponseEntity<List<Reserva>> findAll(){
        List<Reserva> lista = service.findAll();
        return ResponseEntity.ok(lista);
    }


}
