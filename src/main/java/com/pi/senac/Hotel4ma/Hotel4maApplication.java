package com.pi.senac.Hotel4ma;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableJpaAuditing //config necessaria para usar @EntityListeners(AuditingEntityListener.class) na model
public class Hotel4maApplication {

	public static void main(String[] args) {
		SpringApplication.run(Hotel4maApplication.class, args);
	}
}
