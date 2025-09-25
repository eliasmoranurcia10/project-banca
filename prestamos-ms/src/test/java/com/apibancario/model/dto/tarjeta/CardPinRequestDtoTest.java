package com.apibancario.model.dto.tarjeta;

import com.apibancario.project_banca.model.dto.cliente.ClientRequestDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class CardPinRequestDtoTest {

    private CardPinRequestDto cardPinRequestDto;
    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        cardPinRequestDto = new CardPinRequestDto(
                "8525",
                "6965"
        );
    }

    @Test
    void testCardPinRequestDtoGetter() {
        assertNotNull(cardPinRequestDto);
        assertEquals("8525", cardPinRequestDto.oldPin());
        assertEquals("6965", cardPinRequestDto.newPin());
    }

    @Test
    void whenCardPinRequestDtoIsValid_thenNoValidationErrors() {
        Set<ConstraintViolation<CardPinRequestDto>> validationErrors = validator.validate(cardPinRequestDto);
        assertTrue(validationErrors.isEmpty(), "El DTO válido no debería tener errores de validación");
    }

    @Test
    void whenCardPinRequestDtoIsNotValid_thenValidationErrors() {
        CardPinRequestDto cardPinRequestDtoFail = new CardPinRequestDto(
                "852554",
                "6965"
        );
        Set<ConstraintViolation<CardPinRequestDto>> validationErrors = validator.validate(cardPinRequestDtoFail);

        assertFalse(validationErrors.isEmpty(), "Es falso que el DTO no tiene errores de validación ");
        assertTrue(validationErrors.stream().anyMatch(
                ve -> ve.getMessage().equals("La clave tiene 4 dígitos"))
        );
    }
}
