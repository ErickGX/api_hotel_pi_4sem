package com.pi.senac.Hotel4ma.controller.old;

import com.pi.senac.Hotel4ma.dtos.InstalacaoAlugavel.dtoGenerics.InstalacaoCustoRequestDTO;
import com.pi.senac.Hotel4ma.dtos.InstalacaoAlugavel.dtoGenerics.InstalacaoCustoResponseDTO;
import com.pi.senac.Hotel4ma.dtos.InstalacaoAlugavel.salaoDeConferencia.Request.SalaoDeConferenciaRequestDTO;
import com.pi.senac.Hotel4ma.dtos.InstalacaoAlugavel.salaoDeConferencia.Response.SalaoDeConferenciaResponseDTO;
import com.pi.senac.Hotel4ma.enums.TipoSalaConferencia;
import com.pi.senac.Hotel4ma.service.SalaoDeConferenciaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/saloes")
@RequiredArgsConstructor
public class SalaoDeConferenciaController {

    private final SalaoDeConferenciaService service;

    @PostMapping
    public ResponseEntity<SalaoDeConferenciaResponseDTO> cadastrar(@RequestBody @Valid SalaoDeConferenciaRequestDTO request) {
        return ResponseEntity.ok(service.cadastrar(request));
    }

    @PostMapping("/custo")
    public ResponseEntity<InstalacaoCustoResponseDTO> cadastrarComCusto(@RequestBody @Valid InstalacaoCustoRequestDTO request) {
        return ResponseEntity.ok(service.cadastrarComCusto(request));
    }

    @GetMapping("/simulacao/{tipo}/{precoBase}/{horas}")
    public ResponseEntity<InstalacaoCustoResponseDTO> simularCustoGenerico(
            @PathVariable TipoSalaConferencia tipo,
            @PathVariable BigDecimal precoBase,
            @PathVariable int horas
    ) {
        BigDecimal custo = precoBase.multiply(BigDecimal.valueOf(horas));
        InstalacaoCustoResponseDTO dto = new InstalacaoCustoResponseDTO(
                null,
                tipo.name(),
                horas,
                custo
        );
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<List<SalaoDeConferenciaResponseDTO>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SalaoDeConferenciaResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SalaoDeConferenciaResponseDTO> atualizar(@PathVariable Long id, @RequestBody @Valid SalaoDeConferenciaRequestDTO request) {
        return ResponseEntity.ok(service.atualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/custo/{horas}")
    public ResponseEntity<InstalacaoCustoResponseDTO> calcularCusto(@PathVariable Long id, @PathVariable int horas) {
        return ResponseEntity.ok(service.calcularCusto(id, horas));
    }
}