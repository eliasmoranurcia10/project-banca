package com.apibancario.model.dto.cliente;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class ClientRequestDtoTest {

    private ClientRequestDto clientRequestDto;
    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        clientRequestDto = new ClientRequestDto(
                "Juan",
                "Pérez",
                "12345678",
                "juan.perez@mail.com"
        );
    }

    @Test
    void testClientRequestDtoGetter() {
        assertNotNull(clientRequestDto);
        assertEquals("Juan", clientRequestDto.name());
        assertEquals("Pérez", clientRequestDto.lastName());
        assertEquals("12345678", clientRequestDto.dni());
        assertEquals("juan.perez@mail.com", clientRequestDto.email());
    }

    @Test
    void whenClientRequestDtoIsValid_thenNoValidationErrors() {

        Set<ConstraintViolation<ClientRequestDto>> validationErrors = validator.validate(clientRequestDto);

        assertTrue(validationErrors.isEmpty(), "El DTO válido no debería tener errores de validación");
    }

    @Test
    void whenClientRequestDtoIsNotValid_thenValidationErrors() {
        ClientRequestDto clientRequestDtoFail = new ClientRequestDto(
                "Juan",
                "Pérez",
                "123",
                ""
        );

        Set<ConstraintViolation<ClientRequestDto>> validationErrors = validator.validate(clientRequestDtoFail);


        assertFalse(validationErrors.isEmpty(), "Es falso que el DTO no tiene errores de validación ");
        assertTrue(validationErrors.stream().anyMatch(
                ve -> ve.getMessage().equals("El DNI debe ser exactamente 8 dígitos"))
        );
        assertTrue(validationErrors.stream().anyMatch(
                ve -> ve.getMessage().equals("El email es obligatorio"))
        );
    }
}
