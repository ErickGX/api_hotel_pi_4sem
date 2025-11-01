package com.pi.senac.Hotel4ma.factory;

import com.pi.senac.Hotel4ma.dtos.Instalacao.Request.InstalacaoRequest;
import com.pi.senac.Hotel4ma.enums.*;
import com.pi.senac.Hotel4ma.model.*;
import com.pi.senac.Hotel4ma.security.sanitizer.InputSanitizer;
// Não precisamos da exceção customizada se o GlobalExceptionHandler já pega a padrão
// import com.pi.senac.Hotel4ma.exceptions.IllegalArgumentException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InstalacaoFactory {

    private final InputSanitizer sanitizer;

    /**
     * Ponto de entrada principal da factory.
     * Valida os campos obrigatórios ANTES de tentar criar o objeto.
     */
    public InstalacaoAlugavel criarInstalacao(InstalacaoRequest dto) {

        // --- 1. Validação "Fail-Fast" ---
        // Valida os campos que são *sempre* obrigatórios, independentemente do tipo.

        String tipo = validarStringObrigatoria(dto.tipo(), "tipo");
        String categoria = validarStringObrigatoria(dto.categoria(), "categoria");

        // --- 2. Delega a criação para o switch ---
        // Agora sabemos que 'tipo' não é nulo e está em maiúsculas.

        return switch (tipo) {
            case "SPA" -> {
                Spa spa = new Spa();
                spa.setTipoSpa(parseEnum(TipoSpa.class, categoria, "categoria (TipoSpa)"));
                yield spa;
            }
            case "SAUNA" -> {
                Sauna sauna = new Sauna();
                sauna.setTipoSauna(parseEnum(TipoSauna.class, categoria, "categoria (TipoSauna)"));
                yield sauna;
            }
            case "QUARTO" -> {
                // Validação específica para QUARTO
                String numeroQuarto = validarStringObrigatoria(dto.numeroQuarto(), "numeroQuarto");

                Quarto quarto = new Quarto();
                quarto.setNumeroQuarto(sanitizer.sanitizeText(numeroQuarto));
                quarto.setTipoQuarto(parseEnum(TipoQuarto.class, categoria, "categoria (TipoQuarto)"));
                yield quarto;
            }
            case "AUDITORIO" -> {
                Auditorio auditorio = new Auditorio();
                auditorio.setTipoAuditorio(parseEnum(TipoAuditorio.class, categoria, "categoria (TipoAuditorio)"));
                yield auditorio;
            }
            case "SALAODECONFERENCIA" -> {
                SalaoDeConferencia conferencia = new SalaoDeConferencia();
                conferencia.setTipoSalaConferencia(parseEnum(TipoSalaConferencia.class, categoria, "categoria (TipoSalaConferencia)"));
                yield conferencia;
            }
            case "SALAODEEVENTOS" -> {
                SalaoDeEventos eventos = new SalaoDeEventos();
                eventos.setTipoSalaoEventos(parseEnum(TipoSalaoEventos.class, categoria, "categoria (TipoSalaoEventos)"));
                yield eventos;
            }
            // Mensagem de erro amigável se o 'tipo' for inválido
            default -> throw new IllegalArgumentException("Valor inválido fornecido para o campo 'tipo': " + dto.tipo());
        };
    }

    /**
     * Metodo utilitário privado para validar e converter Strings para Enums.
     * Lança uma exceção clara se o valor for nulo, vazio ou inválido.
     *
     * @param enumClass A classe do Enum (ex: TipoSpa.class)
     * @param value A String recebida do DTO
     * @param fieldName O nome do campo para a mensagem de erro
     * @return A constante Enum correspondente
     */
    private <E extends Enum<E>> E parseEnum(Class<E> enumClass, String value, String fieldName) {
        // A validação de nulo/vazio já foi feita pelo validarStringObrigatoria
        try {
            return Enum.valueOf(enumClass, value.toUpperCase().trim());
        } catch (IllegalArgumentException ex) {
            // Captura o erro "No enum constant..." e lança um erro mais amigável
            throw new IllegalArgumentException("Valor inválido fornecido para o campo '" + fieldName + "': " + value);
        }
    }

    /**
     * Metodo utilitário privado para checar se uma String não é nula nem vazia.
     * Retorna a String em maiúsculas e sem espaços extras, pronta para uso.
     *
     * @param value O valor da String do DTO
     * @param fieldName O nome do campo para a mensagem de erro
     * @return A String validada e normalizada
     */
    private String validarStringObrigatoria(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("O campo '" + fieldName + "' é obrigatório.");
        }
        return value.toUpperCase().trim();
    }
}