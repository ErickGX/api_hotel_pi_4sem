package com.pi.senac.Hotel4ma.config;

import com.pi.senac.Hotel4ma.enums.Role;
import com.pi.senac.Hotel4ma.model.Administrador;
import com.pi.senac.Hotel4ma.repository.AdministradorRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class DataSeeder {

    private final AdministradorRepository adminRepository;
    private final PasswordEncoderConfig passwordEncoder;
    private static final Logger log = LoggerFactory.getLogger(DataSeeder.class);

    /**
     * Cria um administrador padrão quando o banco está vazio.
     */
    @Bean
    public CommandLineRunner createDefaultAdmin() {
        return args -> {
            // se já existir um admin, não faz nada
            if (adminRepository.count() > 0) {
                log.warn("Admin já existente — nenhum novo criado.");
                return;
            }
            // cria o admin padrão
            Administrador admin = new Administrador();
            admin.setNome("Administrador do Sistema");
            admin.setEmail("qa@gmail.com");
            admin.setSenha(passwordEncoder.bCryptPasswordEncoder().encode("qa12345"));
            admin.setRole(Role.ADMIN);
            admin.setCpf("08073305070");
            admin.setTelefone("11940028922");
            adminRepository.save(admin);
            log.info("Admin padrão criado com sucesso!");
            log.warn(" Email: qa@gmail.com");
            log.warn(" Senha: qa12345");
        };
    }
}