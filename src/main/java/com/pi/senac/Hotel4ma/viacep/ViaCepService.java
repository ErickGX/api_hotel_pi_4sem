package com.pi.senac.Hotel4ma.viacep;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ViaCepService {

    private final WebClient webClient = WebClient.create("https://viacep.com.br/ws");

    public Mono<EnderecoResponse> buscarEnderecoPorCep(String cep) {
        return webClient.get()
                .uri("/{cep}/json", cep)
                .retrieve()
                .bodyToMono(EnderecoResponse.class);
    }
}