package com.pi.senac.Hotel4ma.seeds;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class DatabaseSeeder implements CommandLineRunner {

    private final ClienteFisicoSeeder clienteFisicoSeeder;
    private final ClienteJuridicoSeeder clienteJuridicoSeeder;
    private final HotelSeeder hotelSeeder;
    private final InstalacaoSeeder instalacaoSeeder;
    private final ReservaSeeder reservaSeeder;
    private final FuncionarioSeeder funcionarioSeeder;
    private final AdminSeeder adminSeeder;

    @Override
    public void run(String... args) throws Exception {

        log.warn("Iniciando população da base de dados (DEV)...");

        hotelSeeder.populate();
        adminSeeder.populate();
        funcionarioSeeder.populate();
        clienteFisicoSeeder.populate();
        //clienteJuridicoSeeder.populate();
        instalacaoSeeder.populate();
        reservaSeeder.populate();

        log.warn("população do BD concluida com Sucesso");


    }
}
