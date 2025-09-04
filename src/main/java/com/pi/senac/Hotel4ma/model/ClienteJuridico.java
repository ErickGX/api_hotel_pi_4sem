package com.pi.senac.Hotel4ma.model;

import com.pi.senac.Hotel4ma.validation.Cnpj;
import jakarta.persistence.*;

@Entity
@DiscriminatorValue("JURIDICO")
public class ClienteJuridico extends Cliente  {

    @Embedded
    @AttributeOverride(name = "numero", column = @Column(name = "cnpj", length = 14, unique = true))
    private Cnpj cnpj;

}
