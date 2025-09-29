package com.apibancario.model.dto.tarjeta;

import com.apibancario.model.enums.TipoTarjeta;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class CardRequestDtoTest {

    private CardRequestDto cardRequestDto;
    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        cardRequestDto = new CardRequestDto(
                TipoTarjeta.DEBITO,
                "1025",
                1
        );
    }

    @Test
    void testCardRequestDtoGetter() {
        assertNotNull(cardRequestDto);
        assertEquals(TipoTarjeta.DEBITO, cardRequestDto.cardType());
        assertEquals("1025", cardRequestDto.cardPin());
        assertEquals(1, cardRequestDto.accountId());
    }

    @Test
    void whenCardRequestDtoIsValid_thenNoValidationErrors() {
        Set<ConstraintViolation<CardRequestDto>> validationErrors = validator.validate(cardRequestDto);
        assertTrue(validationErrors.isEmpty(), "El DTO válido no debería tener errores de validación");
    }

    @Test
    void whenCardRequestDtoIsNotValid_thenValidationErrors() {
        CardRequestDto cardRequestDtoFail = new CardRequestDto(
                TipoTarjeta.DEBITO,
                "",
                -5
        );

        Set<ConstraintViolation<CardRequestDto>> validationErrors = validator.validate(cardRequestDtoFail);

        assertFalse(validationErrors.isEmpty(), "Es falso que el DTO no tiene errores de validación ");
        assertTrue(validationErrors.stream().anyMatch(
                ve -> ve.getMessage().equals("La clave no tiene que ser vacío"))
        );
        assertTrue(validationErrors.stream().anyMatch(
                ve -> ve.getMessage().equals("El id de cuenta debe ser un número positivo"))
        );
    }

}
