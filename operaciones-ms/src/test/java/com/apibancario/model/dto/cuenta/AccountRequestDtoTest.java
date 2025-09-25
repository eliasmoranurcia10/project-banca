package com.apibancario.model.dto.cuenta;

import com.apibancario.project_banca.model.dto.cliente.ClientRequestDto;
import com.apibancario.project_banca.model.enums.TipoCuenta;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class AccountRequestDtoTest {

    private AccountRequestDto accountRequestDto;
    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        accountRequestDto = new AccountRequestDto(
                TipoCuenta.AHORRO,
                "254855",
                new BigDecimal("528.00"),
                1
        );
    }

    @Test
    void testAccountRequestDtoGetter() {
        assertNotNull(accountRequestDto);
        assertEquals(TipoCuenta.AHORRO, accountRequestDto.accountType());
        assertEquals("254855", accountRequestDto.password());
        assertEquals(new BigDecimal("528.00"), accountRequestDto.saldo());
        assertEquals(1, accountRequestDto.clientId());
    }

    @Test
    void whenAccountRequestDtoIsValid_thenNoValidationErrors() {
        Set<ConstraintViolation<AccountRequestDto>> validationErrors = validator.validate(accountRequestDto);
        assertTrue(validationErrors.isEmpty(), "El DTO válido no debería tener errores de validación");
    }

    @Test
    void whenAccountRequestDtoIsNotValid_thenValidationErrors() {
        AccountRequestDto accountRequestDtoFail = new AccountRequestDto(
                TipoCuenta.AHORRO,
                "254",
                new BigDecimal("5.00"),
                null
        );

        Set<ConstraintViolation<AccountRequestDto>> validationErrors = validator.validate(accountRequestDtoFail);


        assertFalse(validationErrors.isEmpty(), "Es falso que el DTO no tiene errores de validación ");
        assertTrue(validationErrors.stream().anyMatch(
                ve -> ve.getMessage().equals("La clave tiene 6 dígitos"))
        );
        assertTrue(validationErrors.stream().anyMatch(
                ve -> ve.getMessage().equals("El cliente no debe ser nulo"))
        );
    }

}
