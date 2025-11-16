package com.pi.senac.Hotel4ma.seeds;

import com.github.javafaker.Faker;
import com.pi.senac.Hotel4ma.enums.CargoFuncionario;
import com.pi.senac.Hotel4ma.enums.Role;
import com.pi.senac.Hotel4ma.model.Funcionario;
import com.pi.senac.Hotel4ma.model.Hotel;
import com.pi.senac.Hotel4ma.service.FuncionarioService;
import com.pi.senac.Hotel4ma.utils.FakerDocumentProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.Random;

@Component
@RequiredArgsConstructor
@Slf4j
public class FuncionarioSeeder {

    private final FuncionarioService funcionarioService;

    private final Faker faker = new Faker(Locale.forLanguageTag("pt-BR"));
    private final Random random = new Random();


    private static final int QTD_FUNCIONARIOS = 40;

    public void populate(){

        if (funcionarioService.existsData()) {
            log.info("FuncionarioSeeder ignorado — já existem funcionários cadastrados.");
            return;
        }

        Hotel hotel = new Hotel();
        hotel.setId(1L);

        for (int i = 0; i < 50; i++) {
            Funcionario funcionario = new Funcionario();

            funcionario.setNome(faker.name().fullName());
            String email = faker.internet().emailAddress();
            email = email.replace("@", i + "@"); //isso me garante que nunca vai existir um email igual na geração
            funcionario.setEmail(email);
            funcionario.setSenha(faker.internet().password()); // pode trocar depois
            funcionario.setTelefone(faker.numerify("119########"));
            funcionario.setCpf(FakerDocumentProvider.cpf());
            funcionario.setRole(Role.FUNCIONARIO); // funcionário é um usuário do sistema
            funcionario.setCargo(faker.options().option(
                    CargoFuncionario.ANALISTA_FINANCEIRO,
                    CargoFuncionario.CHEF_EXECUTIVO,
                    CargoFuncionario.CONCIERGE,
                    CargoFuncionario.DIRETOR_OPERACOES,
                    CargoFuncionario.GERENTE_HOSPEDAGEM,
                    CargoFuncionario.SUPERVISOR_RECEPCAO,
                    CargoFuncionario.ANALISTA_RESERVAS,
                    CargoFuncionario.DIRETOR_FINANCEIRO,
                    CargoFuncionario.GERENTE_AB,
                    CargoFuncionario.GERENTE_GERAL
            ));
            funcionario.setHotel(hotel);

            funcionarioService.saveFuncionarioSeeder(funcionario);
        }


    }


}

