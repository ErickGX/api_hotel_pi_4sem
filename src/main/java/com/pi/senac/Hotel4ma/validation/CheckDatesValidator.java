package com.pi.senac.Hotel4ma.validation;

import com.pi.senac.Hotel4ma.model.Reserva;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.LocalDateTime;

//define que a anotação @CheckDates será usada na classe Reserva
public class CheckDatesValidator implements ConstraintValidator<CheckDates, Reserva> {

    @Override
    public boolean isValid(Reserva reserva, ConstraintValidatorContext constraintValidatorContext) {
        if (reserva == null) {
                return true;
        }

        LocalDate checkIn = reserva.getCheckIn();
        LocalDate checkOut = reserva.getCheckOut();

        //valida se os campos nao sao nulos
        if (checkIn == null || checkOut == null) {
            return true; // @NotNull cuidará dessa validação
        }

        // CheckIn não pode estar no passado
        if (checkIn.isBefore(checkOut)) {
            return false;
        }

        return checkOut.isAfter(checkIn);
    }
}
