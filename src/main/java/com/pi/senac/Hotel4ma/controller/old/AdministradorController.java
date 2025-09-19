package com.pi.senac.Hotel4ma.controller.old;


import com.pi.senac.Hotel4ma.service.AdministradorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdministradorController implements GenericController {

    private final AdministradorService service;
}
