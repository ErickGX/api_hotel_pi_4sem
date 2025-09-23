package com.pi.senac.Hotel4ma.controller;


import com.pi.senac.Hotel4ma.dtos.Cliente.Request.ClienteFisicoRequest;
import com.pi.senac.Hotel4ma.dtos.Cliente.Request.ClienteJuridicoRequest;
import com.pi.senac.Hotel4ma.dtos.Cliente.Request.ClienteUpdateRequest;
import com.pi.senac.Hotel4ma.dtos.Cliente.Response.ClienteResponseDTO;
import com.pi.senac.Hotel4ma.service.ClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("api/clientes")
@RequiredArgsConstructor
public class ClienteController implements GenericController {

    private final ClienteService service;

    private static final String base_path = "api/clientes";

    @PostMapping("/fisico")
    public ResponseEntity<Void> createFisico(
            @RequestBody @Valid ClienteFisicoRequest request) {

        ClienteResponseDTO response = service.createFisico(request);
        URI location = gerarHeaderLocation(base_path, response.id());
        return ResponseEntity.created(location).build();
    }

    @PostMapping("/juridico")
    public ResponseEntity<ClienteResponseDTO> createJuridico(@RequestBody @Valid ClienteJuridicoRequest request) {
        ClienteResponseDTO response = service.createJuridico(request);

        URI location = gerarHeaderLocation(base_path, response.id());
        return ResponseEntity.created(location).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ClienteResponseDTO>> findAll() {
        return ResponseEntity.ok(service.listAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<ClienteResponseDTO> findById(@PathVariable("id") Long id) {
        var cliente = service.findById(id);
        if (cliente == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cliente);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/fisico/{id}")
    public ResponseEntity<Void> atualizarFisico(
            @PathVariable("id") Long id,
            @RequestBody @Valid ClienteUpdateRequest request) {

        service.updateClientFisico(request, id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/juridico/{id}")
    public ResponseEntity<Void> atualizarJuridico(
            @PathVariable("id") Long id,
            @RequestBody @Valid ClienteUpdateRequest request) {

        service.updateClientJuridico(request, id);
        return ResponseEntity.ok().build();
    }

}
