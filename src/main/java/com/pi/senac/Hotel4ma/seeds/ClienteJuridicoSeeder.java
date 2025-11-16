package com.pi.senac.Hotel4ma.seeds;


import com.github.javafaker.Faker;
import com.pi.senac.Hotel4ma.model.ClienteJuridico;
import com.pi.senac.Hotel4ma.service.ClienteService;
import com.pi.senac.Hotel4ma.utils.FakerDocumentProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@RequiredArgsConstructor
@Slf4j
public class ClienteJuridicoSeeder {

    private final ClienteService clienteService;
    private final Faker faker = new Faker(Locale.forLanguageTag("pt-BR"));


    public void populate(){


        //verificador para seeders nao rodarem toda vez que a api inicia
        if (clienteService.existsData()) {
            log.info("ClienteJuridicoSeeder ignorado — já existem clientes cadastrados.");
            return;
        }

        for (int i = 0; i < 500 ; i++) {
            ClienteJuridico clienteJuridico = new ClienteJuridico();
            clienteJuridico.setCnpj(FakerDocumentProvider.cnpj());
            clienteJuridico.setNome(faker.name().firstName());
            String email = faker.internet().emailAddress();
            email = email.replace("@", i + "@"); //isso me garante que nunca vai existir um email igual na geração
            clienteJuridico.setEmail(email);
            clienteJuridico.setSenha(faker.internet().password());
            clienteJuridico.setTelefone(faker.numerify("119########"));

            //dados de endereço
            String cepComMascara = faker.address().zipCode(); // Ex: "54321-098"
            String cepSemMascara = cepComMascara.replace("-", "");
            clienteJuridico.setCep(cepSemMascara);
            clienteJuridico.setLogradouro(faker.address().streetName());
            clienteJuridico.setNumero(faker.address().buildingNumber());
            clienteJuridico.setComplemento(faker.address().secondaryAddress());
            clienteJuridico.setBairro(faker.address().cityPrefix());
            clienteJuridico.setLocalidade(faker.address().city());
            clienteJuridico.setUf(faker.address().stateAbbr());

            var idGerado =  clienteService.createJuridicoSeeder(clienteJuridico);
            log.info("Cliente Juridico Seeder : " + idGerado);

        }
    }
}
