package com.pi.senac.Hotel4ma.controller;


import com.pi.senac.Hotel4ma.dtos.Pagamento.Response.PagamentoResponseDTO;
import com.pi.senac.Hotel4ma.service.PagamentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/pagamentos")
public class PagamentoController {

    private final PagamentoService service;


    @GetMapping
    public ResponseEntity<List<PagamentoResponseDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<PagamentoResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }
}
