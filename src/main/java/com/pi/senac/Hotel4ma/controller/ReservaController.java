package com.pi.senac.Hotel4ma.controller;


import com.pi.senac.Hotel4ma.dtos.Reserva.Request.ReservaRequest;
import com.pi.senac.Hotel4ma.dtos.Reserva.Response.ReservaResponseDTO;
import com.pi.senac.Hotel4ma.service.ReservaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reservas")
public class ReservaController implements GenericController{

    private final ReservaService service;
    private static String base_path = "/api/instalacoes";

    /**
     * Cria uma nova reserva utilizando apenas cliente ativos
     * Clientes inativos não podem criar reservas
     * @param dto
     * @return
     */
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'FUNCIONARIO', 'CLIENTE')")
    public ResponseEntity<ReservaResponseDTO> create(@RequestBody ReservaRequest dto){
        ReservaResponseDTO criada  =  service.save(dto);
        URI location =  gerarHeaderLocation(base_path, criada.id());
        return ResponseEntity.created(location).build();
    }

    /**
     * Lista todas as reservas do sistema
     * Acesso permitido apenas para ADMIN e FUNCIONARIO
     * @return Lista de reservas
     */
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'FUNCIONARIO')")
    public ResponseEntity<List<ReservaResponseDTO>> findAll(){
        List<ReservaResponseDTO> lista = service.findAll();
        return ResponseEntity.ok(lista);
    }


}
