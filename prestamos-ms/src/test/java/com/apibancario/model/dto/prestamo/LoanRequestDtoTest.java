package com.apibancario.model.dto.prestamo;

import com.apibancario.model.enums.EstadoPrestamo;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class LoanRequestDtoTest {

    private LoanRequestDto loanRequestDto;
    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        loanRequestDto = new LoanRequestDto(
                new BigDecimal("4000.00"),
                new BigDecimal("0.30"),
                14,
                EstadoPrestamo.APROBADO,
                1
        );
    }

    @Test
    void testLoanRequestDtoGetter() {
        assertNotNull(loanRequestDto);
        assertEquals(new BigDecimal("4000.00"), loanRequestDto.totalAmount());
        assertEquals(new BigDecimal("0.30"), loanRequestDto.interestRate());
        assertEquals(14, loanRequestDto.monthsOfDeadline());
        assertEquals(EstadoPrestamo.APROBADO, loanRequestDto.status());
        assertEquals(1, loanRequestDto.clientId());
    }

    @Test
    void whenLoanRequestDtoIsValid_thenNoValidationErrors() {
        Set<ConstraintViolation<LoanRequestDto>> validationErrors = validator.validate(loanRequestDto);
        assertTrue(validationErrors.isEmpty(), "El DTO válido no debería tener errores de validación");
    }

    @Test
    void whenLoanRequestDtoIsNotValid_thenValidationErrors() {
        LoanRequestDto loanRequestDtoFail = new LoanRequestDto(
                new BigDecimal("4000.00"),
                new BigDecimal("2.30"),
                80,
                EstadoPrestamo.APROBADO,
                1
        );

        Set<ConstraintViolation<LoanRequestDto>> validationErrors = validator.validate(loanRequestDtoFail);

        assertFalse(validationErrors.isEmpty(), "Es falso que el DTO no tiene errores de validación ");
        assertTrue(validationErrors.stream().anyMatch(
                ve -> ve.getMessage().equals("La tasa de interés no puede ser mayor a 1 (100%)"))
        );
        assertTrue(validationErrors.stream().anyMatch(
                ve -> ve.getMessage().equals("El plazo máximo es de 72 meses"))
        );
    }

}
