package com.pi.senac.Hotel4ma.seeds;

import com.github.javafaker.Faker;
import com.pi.senac.Hotel4ma.model.Hotel;
import com.pi.senac.Hotel4ma.service.HotelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@RequiredArgsConstructor
@Slf4j
public class HotelSeeder {

    private final HotelService hotelService;
    private final Faker faker = new Faker(Locale.forLanguageTag("pt-BR"));




    public void populate(){

        //verificador para seeders nao rodarem toda vez que a api inicia
        if (hotelService.existsData()) {
            log.info("HotelSeeder ignorado — já existem hoteis cadastrados.");
            return;
        }


        for (int i = 0; i < 5; i++) {
            Hotel hotel = new Hotel();
            hotel.setNome(faker.company().name());
            hotelService.saveHotelSeeder(hotel);
        }

        log.info("Hotel Seeder Finalizado");
    }

}
