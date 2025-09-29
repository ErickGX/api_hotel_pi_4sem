package com.pi.senac.Hotel4ma.factory;

import com.pi.senac.Hotel4ma.dtos.Instalacao.Request.InstalacaoRequest;
import com.pi.senac.Hotel4ma.enums.*;
import com.pi.senac.Hotel4ma.model.*;
import com.pi.senac.Hotel4ma.security.sanitizer.InputSanitizer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor//para poder ser injetada nas camadas necessarias
public class InstalacaoFactory {

    private final InputSanitizer sanitizer;

    //Mappers nao lidam com classes abstratas
    //necessario criar uma factory e usar o tipo como decisao
    //para fazer a escolha correta da instanciação baseada no tipo
    //retorno é a instanciacao das subclasses concreta vazia
    public InstalacaoAlugavel criarInstalacao(InstalacaoRequest dto) {
        return switch (dto.tipo().toUpperCase()) {
            case "SPA" -> {
                Spa spa = new Spa();
                spa.setTipoSpa(TipoSpa.valueOf(dto.categoria()));
                yield spa;
            }
            case "SAUNA" -> {
                Sauna sauna = new Sauna();
                sauna.setTipoSauna(TipoSauna.valueOf(dto.categoria()));
                yield sauna;
            }
            case "QUARTO" -> {
                Quarto quarto = new Quarto();
                String numeroQuartoSanitizado = sanitizer.sanitizeText(String.valueOf(dto.numeroQuarto()));
                quarto.setTipoQuarto(TipoQuarto.valueOf(dto.categoria()));
                quarto.setNumeroQuarto(numeroQuartoSanitizado);
                yield quarto;
            }
            case "AUDITORIO" -> {
                Auditorio auditorio = new Auditorio();
                auditorio.setTipoAuditorio(TipoAuditorio.valueOf(dto.categoria()));
                yield auditorio;
            }
            case "SALAODECONFERENCIA" -> {
                SalaoDeConferencia conferencia = new SalaoDeConferencia();
                conferencia.setTipoSalaConferencia(TipoSalaConferencia.valueOf(dto.categoria()));
                yield conferencia;
            }
            case "SALAODEEVENTOS" -> {
                SalaoDeEventos eventos = new SalaoDeEventos();
                eventos.setTipoSalaoEventos(TipoSalaoEventos.valueOf(dto.categoria()));
                yield eventos;
            }
            default -> throw new IllegalArgumentException("Tipo de instalação inválido: " + dto.tipo());
        };
    }
}
