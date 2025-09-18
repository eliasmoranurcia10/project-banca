package com.apibancario.project_banca.model.dto.cuenta;

import com.apibancario.project_banca.model.dto.cliente.ClientRequestDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class PasswordRequestDtoTest {

    private PasswordRequestDto passwordRequestDto;
    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        passwordRequestDto = new PasswordRequestDto(
                "050505",
                "545625"
        );
    }

    @Test
    void testPasswordRequestDtoGetter() {
        assertNotNull(passwordRequestDto);
        assertEquals("050505", passwordRequestDto.oldPassword());
        assertEquals("545625", passwordRequestDto.newPassword());
    }

    @Test
    void whenPasswordRequestDtoIsValid_thenNoValidationErrors() {

        Set<ConstraintViolation<PasswordRequestDto>> validationErrors = validator.validate(passwordRequestDto);

        assertTrue(validationErrors.isEmpty(), "El DTO válido no debería tener errores de validación");
    }

    @Test
    void whenPasswordRequestDtoIsNotValid_thenValidationErrors() {
        PasswordRequestDto passwordRequestDtoFail = new PasswordRequestDto(
                "",
                "54562"
        );

        Set<ConstraintViolation<PasswordRequestDto>> validationErrors = validator.validate(passwordRequestDtoFail);


        assertFalse(validationErrors.isEmpty(), "Es falso que el DTO no tiene errores de validación ");
        assertTrue(validationErrors.stream().anyMatch(
                ve -> ve.getMessage().equals("La clave no tiene que ser vacío"))
        );
        assertTrue(validationErrors.stream().anyMatch(
                ve -> ve.getMessage().equals("La clave tiene 6 dígitos"))
        );
    }

}
