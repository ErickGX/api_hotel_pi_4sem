package com.pi.senac.Hotel4ma.controller;

import com.pi.senac.Hotel4ma.dtos.Espacos.Request.EspacosRequestDTO;
import com.pi.senac.Hotel4ma.dtos.Espacos.Response.EspacosResponseDTO;
import com.pi.senac.Hotel4ma.service.EspacosService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/espacos")
public class EspacosController implements GenericController {

    private final EspacosService service;
    private static final String base_path = "api/espacos";

    @PostMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> create(
            @RequestBody @Valid EspacosRequestDTO request) {

        Long id_gerado = service.create(request);
        URI location = gerarHeaderLocation(base_path, id_gerado);
        return ResponseEntity.created(location).build();
    }



    @GetMapping
    public ResponseEntity<List<EspacosResponseDTO>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EspacosResponseDTO> findById(@PathVariable("id") Long id) {
        var espaco = service.findById(id);
        if (espaco == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(espaco);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }


}
