package com.pi.senac.Hotel4ma.controller;

import com.pi.senac.Hotel4ma.dtos.Cliente.Request.ClienteFisicoRequest;
import com.pi.senac.Hotel4ma.dtos.Cliente.Request.ClienteJuridicoRequest;
import com.pi.senac.Hotel4ma.dtos.Cliente.Request.ClienteUpdateRequest;
import com.pi.senac.Hotel4ma.dtos.Cliente.Response.ClienteResponseDTO;
import com.pi.senac.Hotel4ma.dtos.Cliente.Response.ClienteResumoDTO;
import com.pi.senac.Hotel4ma.dtos.Cliente.Response.ClienteResumoProjection;
import com.pi.senac.Hotel4ma.service.ClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("api/clientes")
@RequiredArgsConstructor
public class ClienteController implements GenericController {

    private final ClienteService service;
    private static final String base_path = "api/clientes";

    @PostMapping("/fisico")
    public ResponseEntity<Void> createFisico(
            @RequestBody @Valid ClienteFisicoRequest request) {
        Long id_gerado = service.createFisico(request);
        URI location = gerarHeaderLocation(base_path, id_gerado);
        return ResponseEntity.created(location).build();
    }

    @PostMapping("/juridico")
    public ResponseEntity<Void> createJuridico(@RequestBody @Valid ClienteJuridicoRequest request) {
        Long id_gerado  = service.createJuridico(request);
        URI location = gerarHeaderLocation(base_path, id_gerado);
        return ResponseEntity.created(location).build();
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'FUNCIONARIO')")
    public ResponseEntity<List<ClienteResponseDTO>> findAll() {
        return ResponseEntity.ok(service.listAll());
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'FUNCIONARIO', 'CLIENTE')")
    public ResponseEntity<ClienteResponseDTO> findById(@PathVariable("id") Long id) {
        var cliente = service.findDtoById(id);
        return ResponseEntity.ok(cliente);
    }


    /**
     * Metodo para Soft Delete de Cliente por ID
     * @param id
     * @return 204 No Content
     */
    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.desativarById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/fisico/{id}")
    @PreAuthorize("hasAnyRole('CLIENTE', 'ADMIN')")
    public ResponseEntity<Void> atualizarFisico(
            @PathVariable("id") Long id,
            @RequestBody @Valid ClienteUpdateRequest request) {

        service.updateClienteFisico(request, id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/juridico/{id}")
    @PreAuthorize("hasAnyRole('CLIENTE', 'ADMIN')") //apenas clientes podem atualizar seus dados, pagina de perfil
    public ResponseEntity<Void> atualizarJuridico(
            @PathVariable("id") Long id,
            @RequestBody @Valid ClienteUpdateRequest request) {

        service.updateClienteJuridico(request, id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/inativos")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ClienteResponseDTO>> findAllInactive() {
        List<ClienteResponseDTO> clientesInativos = service.getInativos();
        return ResponseEntity.ok(clientesInativos);
    }

    @GetMapping("/inativos/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ClienteResponseDTO> findInactiveById(@PathVariable Long id){
        ClienteResponseDTO clienteInativo = service.getInativosById(id);
        return ResponseEntity.ok(clienteInativo);
    }

    @PatchMapping("/reativar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> reativarCliente(@PathVariable Long id) {
        service.reativarRegistro(id);
        return ResponseEntity.noContent().build();
    }
}
