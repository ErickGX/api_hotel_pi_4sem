package com.pi.senac.Hotel4ma.controller.old;

import com.pi.senac.Hotel4ma.dtos.Espacos.Request.EspacosRequestDTO;
import com.pi.senac.Hotel4ma.dtos.Espacos.Response.EspacosResponseDTO;
import com.pi.senac.Hotel4ma.service.EspacosService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/espacos")
public class EspacosController implements GenericController {

    private final EspacosService service;

    @PostMapping()
    public ResponseEntity<EspacosResponseDTO> createFisico(
            @RequestBody @Valid EspacosRequestDTO request) {

        EspacosResponseDTO response = service.create(request);
        URI location = gerarHeaderLocation("/api/clientes", response.id());
        return ResponseEntity.created(location).build();
    }

    @GetMapping
    public ResponseEntity<List<EspacosResponseDTO>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<EspacosResponseDTO> findById(@PathVariable("id") Long id) {
        var espaco = service.findById(id);
        if (espaco == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(espaco);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }


}
