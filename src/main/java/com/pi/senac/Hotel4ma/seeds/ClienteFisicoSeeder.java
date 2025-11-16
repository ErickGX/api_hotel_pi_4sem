package com.pi.senac.Hotel4ma.seeds;


import com.github.javafaker.Faker;
import com.pi.senac.Hotel4ma.model.ClienteFisico;
import com.pi.senac.Hotel4ma.service.ClienteService;
import com.pi.senac.Hotel4ma.utils.FakerDocumentProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@RequiredArgsConstructor
@Slf4j
public class ClienteFisicoSeeder {


    private final ClienteService clienteService;
    private final Faker faker = new Faker(Locale.forLanguageTag("pt-BR"));

    public void populate(){


        //verificador para seeders nao rodarem toda vez que a api inicia
        if (clienteService.existsData()) {
            log.info("ClienteFisicoSeeder ignorado — já existem clientes cadastrados.");
            return;
        }

        for (int i = 0; i < 200 ; i++) {
            ClienteFisico clienteFisico = new ClienteFisico();
            clienteFisico.setCpf(FakerDocumentProvider.cpf());
            clienteFisico.setNome(faker.name().firstName());
            String email = faker.internet().emailAddress();
            email = email.replace("@", i + "@"); //isso me garante que nunca vai existir um email igual na geração
            clienteFisico.setEmail(email);
            clienteFisico.setSenha(faker.internet().password());
            clienteFisico.setTelefone(faker.numerify("119########"));

            //dados de endereço
            String cepComMascara = faker.address().zipCode(); // Ex: "54321-098"
            String cepSemMascara = cepComMascara.replace("-", "");
            clienteFisico.setCep(cepSemMascara);
            clienteFisico.setLogradouro(faker.address().streetName());
            clienteFisico.setNumero(faker.address().buildingNumber());
            clienteFisico.setComplemento(faker.address().secondaryAddress());
            clienteFisico.setBairro(faker.address().cityPrefix());
            clienteFisico.setLocalidade(faker.address().city());
            clienteFisico.setUf(faker.address().stateAbbr());

           var idGerado =  clienteService.createFisicoSeeder(clienteFisico);
           log.info("Cliente Fisico Seeder : " + idGerado);

        }
    }
}
