package com.pi.senac.Hotel4ma.controller.old;


import com.pi.senac.Hotel4ma.dtos.Administrador.Request.AdminRequestDTO;
import com.pi.senac.Hotel4ma.dtos.Cliente.Response.ClienteResponseDTO;
import com.pi.senac.Hotel4ma.service.AdministradorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("api/admin")
@RequiredArgsConstructor
public class AdministradorController implements GenericController {

    private final AdministradorService service;

    //por questoes de segurança só é permitido criar admin
    //Nao permitido nenhuma outra operação
    //apenas logar admin no futuro
    @PostMapping
    public ResponseEntity<Void> create(
            @RequestBody @Valid AdminRequestDTO request) {

        service.create(request);
        return ResponseEntity.ok().build();
    }
}
