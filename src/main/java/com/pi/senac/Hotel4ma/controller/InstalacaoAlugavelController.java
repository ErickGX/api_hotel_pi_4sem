package com.pi.senac.Hotel4ma.controller;

import com.pi.senac.Hotel4ma.dtos.Instalacao.Request.InstalacaoRequest;
import com.pi.senac.Hotel4ma.dtos.Instalacao.Request.InstalacaoUpdateRequest;
import com.pi.senac.Hotel4ma.dtos.Instalacao.Response.InstalacaoResponseDTO;
import com.pi.senac.Hotel4ma.dtos.Instalacao.Response.OrcamentoResponseDTO;
import com.pi.senac.Hotel4ma.enums.*;
import com.pi.senac.Hotel4ma.service.InstalacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.net.URI;
import org.springframework.web.bind.annotation.DeleteMapping;

@RestController
@RequestMapping("/api/instalacoes")
@RequiredArgsConstructor
public class InstalacaoAlugavelController implements GenericController {

    private final InstalacaoService service;

    private static final String base_path = "/api/instalacoes";

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> save(@RequestBody InstalacaoRequest request) {
        Long id_gerado = service.create(request);
        URI location = gerarHeaderLocation(base_path, id_gerado);
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'FUNCIONARIO')")
    public ResponseEntity<InstalacaoResponseDTO> findById(@PathVariable Long id) {
        InstalacaoResponseDTO response = service.findById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping  //rota publica para uso no front-end
    public ResponseEntity<List<InstalacaoResponseDTO>> findAll() {
        List<InstalacaoResponseDTO> response = service.findAll();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<InstalacaoResponseDTO> update(
            @PathVariable Long id,
            @RequestBody InstalacaoUpdateRequest dto) {

        InstalacaoResponseDTO response = service.update(id, dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }


    //olha que bagulho satanico no controller
    //Acesso publico para simular orcamento
    @GetMapping("/orcamento")
    public ResponseEntity<?> simularOrcamento(
            @RequestParam String tipo,
            @RequestParam String classe) {

        FatorMultiplicador fator;

        try {
            switch (classe.toLowerCase()) {
                case "auditorio":
                    fator = TipoAuditorio.valueOf(tipo.toUpperCase());
                    break;
                case "quarto":
                    fator = TipoQuarto.valueOf(tipo.toUpperCase());
                    break;
                case "salaodeconferencia":
                    fator = TipoSalaConferencia.valueOf(tipo.toUpperCase());
                    break;
                case "salaodeeventos":
                    fator = TipoSalaoEventos.valueOf(tipo.toUpperCase());
                    break;
                case "sauna":
                    fator = TipoSauna.valueOf(tipo.toUpperCase());
                    break;
                case "spa":
                    fator = TipoSpa.valueOf(tipo.toUpperCase());
                    break;
                default:
                    return ResponseEntity.badRequest()
                            .body("Classe '" + classe + "' não reconhecida.");
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body("Categoria '" + tipo + "' não reconhecida para classe '" + classe + "'.");
        }

        BigDecimal valorFinal = service.calcularValor(fator);
        OrcamentoResponseDTO response = new OrcamentoResponseDTO(classe, tipo, valorFinal);
        return ResponseEntity.ok(response);
    }

}
