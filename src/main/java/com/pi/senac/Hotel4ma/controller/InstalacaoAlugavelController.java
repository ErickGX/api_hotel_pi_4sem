package com.pi.senac.Hotel4ma.controller;


import com.pi.senac.Hotel4ma.dtos.Instalacao.Request.InstalacaoRequest;
import com.pi.senac.Hotel4ma.dtos.Instalacao.Response.InstalacaoResponseDTO;
import com.pi.senac.Hotel4ma.service.InstalacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api/instalacoes")
@RequiredArgsConstructor
public class InstalacaoAlugavelController implements GenericController {

    private final InstalacaoService service;

    private static String base_path = "/api/instalacoes";

    @PostMapping
    public ResponseEntity<InstalacaoResponseDTO> save(@RequestBody InstalacaoRequest request) {

        InstalacaoResponseDTO response = service.create(request);

        URI location = gerarHeaderLocation(base_path, response.id());
        return ResponseEntity.created(location).body(response);

    }


}
