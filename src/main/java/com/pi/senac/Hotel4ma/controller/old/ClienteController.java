package com.pi.senac.Hotel4ma.controller.old;


import com.pi.senac.Hotel4ma.dtos.Cliente.Request.ClienteFisicoRequest;
import com.pi.senac.Hotel4ma.dtos.Cliente.Request.ClienteJuridicoRequest;
import com.pi.senac.Hotel4ma.dtos.Cliente.Request.ClienteUpdateRequest;
import com.pi.senac.Hotel4ma.dtos.Cliente.Response.ClienteResponseDTO;
import com.pi.senac.Hotel4ma.mappers.ClienteMapper;
import com.pi.senac.Hotel4ma.service.ClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("api/clientes")
@RequiredArgsConstructor
public class ClienteController implements GenericController {

    private final ClienteService service;

    @PostMapping("/fisico")
    public ResponseEntity<ClienteResponseDTO> salvarFisico(@RequestBody @Valid ClienteFisicoRequest request){
        ClienteResponseDTO response = service.salvarFisico(request);

            URI location = gerarHeaderLocation("/api/clientes", response.id());
            return ResponseEntity.created(location).body(response);
    }

    @PostMapping("/juridico")
    public ResponseEntity<ClienteResponseDTO> salvaJuridico(@RequestBody @Valid ClienteJuridicoRequest request){
        ClienteResponseDTO response = service.salvarJuridico(request);

        URI location = gerarHeaderLocation("/api/clientes",response.id());
        return ResponseEntity.created(location).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ClienteResponseDTO>> listar(){
        return ResponseEntity.ok(service.listAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<ClienteResponseDTO> buscarPorId(@PathVariable("id") Long id){
            var cliente = service.buscarPorId(id);
            if (cliente == null){
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(cliente);
    }

    @DeleteMapping("{id}") //Responsa sem corpo VOID
    public ResponseEntity<Void> excluir(@PathVariable("id") Long id){
        Boolean foiDeletado = service.deletarPorId(id);

        if (!foiDeletado){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> atualizar(@PathVariable("id") Long id, @RequestBody @Valid ClienteUpdateRequest request){
            service.atualizarFisico(request, id);
            return ResponseEntity.ok().build();
    }

}
