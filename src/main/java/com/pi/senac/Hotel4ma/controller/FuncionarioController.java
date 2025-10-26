package com.pi.senac.Hotel4ma.controller;


import com.pi.senac.Hotel4ma.dtos.Funcionario.Request.FuncionarioRequest;
import com.pi.senac.Hotel4ma.dtos.Funcionario.Request.FuncionarioUpdateRequest;
import com.pi.senac.Hotel4ma.dtos.Funcionario.Response.FuncionarioResponseDTO;
import com.pi.senac.Hotel4ma.service.FuncionarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/funcionario")
public class FuncionarioController implements GenericController {

    private final FuncionarioService service;
    private static String base_path = "/api/funcionario";

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")//Qualquer Espaço só pode ser criado por um ADMIN
    public ResponseEntity<Void> create(@RequestBody @Valid FuncionarioRequest dto){
        Long id_gerado  = service.saveFuncionario(dto);
        URI location =  gerarHeaderLocation(base_path, id_gerado);
        return ResponseEntity.created(location).build();
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<FuncionarioResponseDTO>> findAll(){
        return ResponseEntity.ok(service.listAll());
    }


    @GetMapping("{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'FUNCIONARIO')")
    public ResponseEntity<FuncionarioResponseDTO> findById(@PathVariable("id") Long id) {
        var cliente = service.findById(id);
        if (cliente == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cliente);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    @PreAuthorize("hasRole('FUNCIONARIO')") //permite que o proprio funcionario atualize seus dados
    public ResponseEntity<Void> update(
            @PathVariable("id") Long id,
            @RequestBody @Valid FuncionarioUpdateRequest request){

        service.update(request, id);
        return ResponseEntity.ok().build();
    }




}
