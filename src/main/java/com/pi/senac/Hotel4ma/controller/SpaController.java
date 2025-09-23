package com.pi.senac.Hotel4ma.controller;

import com.pi.senac.Hotel4ma.dtos.InstalacaoAlugavel.dtoGenerics.InstalacaoCustoResponseDTO;
import com.pi.senac.Hotel4ma.dtos.InstalacaoAlugavel.spa.request.InstalacaoCustoSpaRequestDTO;
import com.pi.senac.Hotel4ma.dtos.InstalacaoAlugavel.spa.request.SpaRequestDTO;
import com.pi.senac.Hotel4ma.dtos.InstalacaoAlugavel.spa.response.SpaResponseDTO;
import com.pi.senac.Hotel4ma.service.SpaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/spas")
@RequiredArgsConstructor
public class SpaController {

    private final SpaService service;

    @PostMapping
    public ResponseEntity<SpaResponseDTO> cadastrar(@RequestBody @Valid SpaRequestDTO dto) {
        return ResponseEntity.ok(service.cadastrar(dto));
    }

    @PostMapping("/custo")
    public ResponseEntity<InstalacaoCustoResponseDTO> cadastrarComCusto(
            @RequestBody @Valid InstalacaoCustoSpaRequestDTO dto
    ) {
        return ResponseEntity.ok(service.cadastrarComCusto(dto));
    }

    @GetMapping("/{id}/custo/{horas}")
    public ResponseEntity<InstalacaoCustoResponseDTO> calcularCusto(@PathVariable Long id, @PathVariable int horas) {
        return ResponseEntity.ok(service.calcularCusto(id, horas));
    }

    @GetMapping
    public ResponseEntity<List<SpaResponseDTO>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SpaResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SpaResponseDTO> atualizar(@PathVariable Long id, @RequestBody @Valid SpaRequestDTO dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}