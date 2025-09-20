package com.pi.senac.Hotel4ma.controller.old;

import com.pi.senac.Hotel4ma.dtos.InstalacaoAlugavel.salaoDeConferencia.Request.SalaoRequestDTO;
import com.pi.senac.Hotel4ma.dtos.InstalacaoAlugavel.salaoDeConferencia.Response.SalaoCustoResponseDTO;
import com.pi.senac.Hotel4ma.dtos.InstalacaoAlugavel.salaoDeConferencia.Response.SalaoResponseDTO;
import com.pi.senac.Hotel4ma.service.SalaoDeConferenciaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/saloes")
public class SalaoDeConferenciaController {

    @Autowired
    private SalaoDeConferenciaService service;

    @PostMapping
    public ResponseEntity<SalaoResponseDTO> cadastrar(@RequestBody @Valid SalaoRequestDTO request) {
        SalaoResponseDTO response = service.cadastrar(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<SalaoResponseDTO>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SalaoResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SalaoResponseDTO> atualizar(@PathVariable Long id, @RequestBody @Valid SalaoRequestDTO request) {
        SalaoResponseDTO atualizado = service.atualizar(id, request);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/{id}/custo/{horas}")
    public ResponseEntity<SalaoCustoResponseDTO> calcularCusto(@PathVariable Long id, @PathVariable int horas) {
        SalaoCustoResponseDTO dto = service.calcularCusto(id, horas);
        return ResponseEntity.ok(dto);
    }
}