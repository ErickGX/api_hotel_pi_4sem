package com.pi.senac.Hotel4ma.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE}) //anotação aplicada na classe
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CheckDatesValidator.class) //indica a classe que vai implementar a lógica de validação.
public @interface CheckDates {
    //mensagem padrão de erro se a validação falhar.
    String message() default "Check-out deve ser após Check-in e Check-in não pode ser no passado";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
