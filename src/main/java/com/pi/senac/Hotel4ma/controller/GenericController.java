package com.pi.senac.Hotel4ma.controller;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

public interface GenericController {

    //reutilizar nos controllers
    default URI gerarHeaderLocation(String basePath, Long id){
        return ServletUriComponentsBuilder
//                .fromCurrentRequest()
//                .path("/{id}")
                .fromPath(basePath + "/{id}")
                .buildAndExpand(id)
                .toUri();
    }

}
