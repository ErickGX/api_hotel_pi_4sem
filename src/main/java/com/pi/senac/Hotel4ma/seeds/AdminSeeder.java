package com.pi.senac.Hotel4ma.seeds;

import com.pi.senac.Hotel4ma.dtos.Administrador.Request.AdminRequestDTO;
import com.pi.senac.Hotel4ma.service.AdministradorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class AdminSeeder {

    private final AdministradorService administradorService;

    /**
     * Metodo padrão de seeders.
     */
    public void populate() {

        // evita criação duplicada
        if (administradorService.existsData()) {
            log.warn("AdminSeeder ignorado — administrador já existe.");
            return;
        }

        log.info("Iniciando AdminSeeder...");

        // DTO como os outros seeders
        AdminRequestDTO dto = new AdminRequestDTO(
                "Administrador do Sistema",
                "qa@gmail.com",
                "11940028922",
                "qa12345",
                "08073305070"
        );

        administradorService.create(dto);

        log.info("Administrador padrão criado com sucesso!");
        log.warn(" Login: qa@gmail.com");
        log.warn(" Senha: qa12345");

        log.info("AdminSeeder finalizado.");
    }
}
