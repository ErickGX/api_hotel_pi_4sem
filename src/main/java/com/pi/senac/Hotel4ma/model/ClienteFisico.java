package com.pi.senac.Hotel4ma.model;

import com.pi.senac.Hotel4ma.validation.Cpf;
import jakarta.persistence.*;
import org.hibernate.validator.constraints.br.CPF;

@Entity
@DiscriminatorValue("FISICO")
public class ClienteFisico extends Cliente{

//    @Embedded
//    @AttributeOverride(name = "numero", column = @Column(name = "cpf", length = 11, unique = true))
    @CPF
    @Column(length = 11, unique = true, nullable = false)
    private Cpf cpf;

}
