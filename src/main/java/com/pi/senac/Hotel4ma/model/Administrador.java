package com.pi.senac.Hotel4ma.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

@Entity
@Getter
@Setter
public class Administrador extends Usuario{

    @Column(unique = true, nullable = false)
    @CPF
    private String cpf;
}
