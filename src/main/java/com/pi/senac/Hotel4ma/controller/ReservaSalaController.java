/*package br.com.pi.hotel.controller;

import br.com.pi.hotel.model.ReservaSala;
import br.com.pi.hotel.service.ReservaSalaService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RequestMapping("/reservasala")
@RestController
public class ReservaSalaController {
    private final ReservaSalaService reservaSalaService;

    public ReservaSalaController(ReservaSalaService reservaSalaService) {
        this.reservaSalaService = reservaSalaService;
    }

    @GetMapping
    public List<ReservaSala> listarTodos() { return reservaSalaService.listarTodos(); }

    @GetMapping("/{id}")
    public ReservaSala buscarPorId(@PathVariable Long id) { return reservaSalaService.buscarPorId(id); }

    @PostMapping
    public ReservaSala salvar(@RequestBody ReservaSala reservaSala) { return reservaSalaService.salvar(reservaSala); }

    @PutMapping("/{id}")
    public ReservaSala atualizar(@PathVariable Long id, @RequestBody ReservaSala reservaSala) { return reservaSalaService.atualizar(id, reservaSala); }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) { reservaSalaService.deletar(id); }
}
*/
package com.pi.senac.Hotel4ma.controller;

import com.pi.senac.Hotel4ma.model.ReservaSala;
import com.pi.senac.Hotel4ma.service.ReservaSalaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

        import java.util.List;

@RestController
@RequestMapping("/reservasalas")
public class ReservaSalaController {

    private final ReservaSalaService reservaSalaService;

    public ReservaSalaController(ReservaSalaService reservaSalaService) {
        this.reservaSalaService = reservaSalaService;
    }

    @GetMapping
    public ResponseEntity<List<ReservaSala>> listarTodos() {
        List<ReservaSala> reservas = reservaSalaService.listarTodos();
        return ResponseEntity.ok(reservas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservaSala> buscarPorId(@PathVariable Long id) {
        ReservaSala reserva = reservaSalaService.buscarPorId(id);
        return reserva != null ? ResponseEntity.ok(reserva) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<ReservaSala> salvar(@RequestBody ReservaSala reservaSala) {
        ReservaSala reservaCriada = reservaSalaService.salvar(reservaSala);
        return ResponseEntity.ok(reservaCriada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReservaSala> atualizar(@PathVariable Long id, @RequestBody ReservaSala reservaSala) {
        ReservaSala reservaAtualizada = reservaSalaService.atualizar(id, reservaSala);
        return reservaAtualizada != null ? ResponseEntity.ok(reservaAtualizada) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        reservaSalaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}