package com.apibancario.model.dto.pagoprestamo;

import com.apibancario.project_banca.model.dto.cliente.ClientRequestDto;
import com.apibancario.project_banca.model.dto.prestamo.LoanResponseDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class LoanPayRequestDtoTest {

    private LoanPayRequestDto loanPayRequestDto;

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        loanPayRequestDto = new LoanPayRequestDto(
                new BigDecimal("400.00"),
                1
        );
    }

    @Test
    void testLoanPayRequestDtoGetter() {
        assertNotNull(loanPayRequestDto);
        assertEquals(new BigDecimal("400.00"), loanPayRequestDto.paymentAmount());
        assertEquals(1, loanPayRequestDto.loanId());
    }

    @Test
    void whenLoanPayRequestDtoIsValid_thenNoValidationErrors() {
        Set<ConstraintViolation<LoanPayRequestDto>> validationErrors = validator.validate(loanPayRequestDto);
        assertTrue(validationErrors.isEmpty(), "El DTO válido no debería tener errores de validación");
    }

    @Test
    void whenLoanPayRequestDtoIsNotValid_thenValidationErrors() {
        LoanPayRequestDto loanPayRequestDtoFail = new LoanPayRequestDto(
                new BigDecimal("-5.00"),
                null
        );

        Set<ConstraintViolation<LoanPayRequestDto>> validationErrors = validator.validate(loanPayRequestDtoFail);

        assertFalse(validationErrors.isEmpty(), "Es falso que el DTO no tiene errores de validación ");
        assertTrue(validationErrors.stream().anyMatch(
                ve -> ve.getMessage().equals("El Monto en un numero positivo"))
        );
        assertTrue(validationErrors.stream().anyMatch(
                ve -> ve.getMessage().equals("Colocar obligatoriamente el id del préstamo"))
        );
    }

}
