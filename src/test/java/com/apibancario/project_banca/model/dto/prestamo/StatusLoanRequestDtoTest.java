package com.apibancario.project_banca.model.dto.prestamo;

import com.apibancario.project_banca.model.dto.cliente.ClientRequestDto;
import com.apibancario.project_banca.model.dto.cliente.ClientResponseDto;
import com.apibancario.project_banca.model.enums.EstadoPrestamo;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class StatusLoanRequestDtoTest {

    private StatusLoanRequestDto statusLoanRequestDto;
    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        statusLoanRequestDto = new StatusLoanRequestDto(
                EstadoPrestamo.APROBADO
        );
    }

    @Test
    void testStatusLoanRequestDtoGetter() {
        assertNotNull(statusLoanRequestDto);
        assertEquals(EstadoPrestamo.APROBADO, statusLoanRequestDto.status());
    }

    @Test
    void whenStatusLoanRequestDtoIsValid_thenNoValidationErrors() {
        Set<ConstraintViolation<StatusLoanRequestDto>> validationErrors = validator.validate(statusLoanRequestDto);
        assertTrue(validationErrors.isEmpty(), "El DTO válido no debería tener errores de validación");
    }

    @Test
    void whenStatusLoanRequestDtoIsNotValid_thenValidationErrors() {
        StatusLoanRequestDto statusLoanRequestDtoFail = new StatusLoanRequestDto(
                null
        );

        Set<ConstraintViolation<StatusLoanRequestDto>> validationErrors = validator.validate(statusLoanRequestDtoFail);

        assertFalse(validationErrors.isEmpty(), "Es falso que el DTO no tiene errores de validación ");
        assertTrue(validationErrors.stream().anyMatch(
                ve -> ve.getMessage().equals("El estado del préstamo es obligatorio"))
        );
    }

}
