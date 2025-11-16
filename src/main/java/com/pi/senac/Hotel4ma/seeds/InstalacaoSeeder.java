package com.pi.senac.Hotel4ma.seeds;

import com.github.javafaker.Faker;
import com.pi.senac.Hotel4ma.enums.TipoQuarto;
import com.pi.senac.Hotel4ma.model.Hotel;
import com.pi.senac.Hotel4ma.model.InstalacaoAlugavel;
import com.pi.senac.Hotel4ma.model.Quarto;
import com.pi.senac.Hotel4ma.service.InstalacaoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@RequiredArgsConstructor
@Slf4j
public class InstalacaoSeeder {

    private final InstalacaoService instalacaoService;
    private final Faker faker = new Faker(Locale.forLanguageTag("pt-BR"));

    public void populate() {

        //verificador para seeders nao rodarem toda vez que a api inicia
        if (instalacaoService.existsData()) {
            log.info("InstalacaoSeeder ignorado — já existem instalações cadastradas.");
            return;
        }

        Hotel hotel = new Hotel();
        hotel.setId(1L);

        for (TipoQuarto tipoQuarto : TipoQuarto.values()) {

            Quarto instalacaoAlugavel = new Quarto();

            instalacaoAlugavel.setHotel(hotel);
            instalacaoAlugavel.setNome("Quarto " + tipoQuarto.name());
            instalacaoAlugavel.setDescricao("Quarto do Tipo : " + tipoQuarto.name());
            instalacaoAlugavel.setTipoQuarto(tipoQuarto);
            String numeroGerado = String.valueOf(faker.number().numberBetween(100, 999));
            instalacaoAlugavel.setNumeroQuarto(numeroGerado);

          var id =  instalacaoService.createSeeder(instalacaoAlugavel);
          log.info("Quarto Gerado com o ID :  " + id +  "  e o tipo : " +instalacaoAlugavel.getTipoQuarto());
        }

    }


}
