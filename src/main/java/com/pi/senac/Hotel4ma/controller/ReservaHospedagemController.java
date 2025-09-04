package com.pi.senac.Hotel4ma.controller;

import com.pi.senac.Hotel4ma.model.ReservaHospedagem;
import com.pi.senac.Hotel4ma.service.ReservaHospedagemService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RequestMapping("/reservahospedagens")
@RestController
public class ReservaHospedagemController {
    private final ReservaHospedagemService reservaHospedagemService;

    public ReservaHospedagemController(ReservaHospedagemService reservaHospedagemService) {
        this.reservaHospedagemService = reservaHospedagemService;
    }

    @GetMapping
    public List<ReservaHospedagem> listarTodos() { return reservaHospedagemService.listarTodos(); }

    @GetMapping("/{id}")
    public ReservaHospedagem buscarPorId(@PathVariable Long id) { return reservaHospedagemService.buscarPorId(id); }

    @PostMapping
    public ReservaHospedagem salvar(@RequestBody ReservaHospedagem reservaHospedagem) { return reservaHospedagemService.salvar(reservaHospedagem); }

    @PutMapping("/{id}")
    public ReservaHospedagem atualizar(@PathVariable Long id, @RequestBody ReservaHospedagem reservaHospedagem) { return reservaHospedagemService.atualizar(id, reservaHospedagem); }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) { reservaHospedagemService.deletar(id); }
}
