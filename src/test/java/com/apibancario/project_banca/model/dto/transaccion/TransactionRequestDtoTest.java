package com.apibancario.project_banca.model.dto.transaccion;

import com.apibancario.project_banca.model.dto.cliente.ClientRequestDto;
import com.apibancario.project_banca.model.enums.TipoTransaccion;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class TransactionRequestDtoTest {

    private TransactionRequestDto transactionRequestDto;
    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        transactionRequestDto = new TransactionRequestDto(
                TipoTransaccion.DEPOSITO,
                new BigDecimal("40.00"),
                "5456585856696365",
                "5447",
                null
        );
    }

    @Test
    void testTransactionRequestDtoGetter() {
        assertNotNull(transactionRequestDto);
        assertEquals(TipoTransaccion.DEPOSITO, transactionRequestDto.transactionType());
        assertEquals(new BigDecimal("40.00"), transactionRequestDto.amount());
        assertEquals("5456585856696365", transactionRequestDto.cardNumber());
        assertEquals("5447", transactionRequestDto.cardPin());
        assertNull( transactionRequestDto.RecipientAccountNumber() );
    }

    @Test
    void whenTransactionRequestDtoIsValid_thenNoValidationErrors() {
        Set<ConstraintViolation<TransactionRequestDto>> validationErrors = validator.validate(transactionRequestDto);
        assertTrue(validationErrors.isEmpty(), "El DTO válido no debería tener errores de validación");
    }

    @Test
    void whenTransactionRequestDtoIsNotValid_thenValidationErrors() {
        TransactionRequestDto transactionRequestDtoFail = new TransactionRequestDto(
                TipoTransaccion.DEPOSITO,
                new BigDecimal("-40.00"),
                "54565858566963658",
                "5447",
                null
        );

        Set<ConstraintViolation<TransactionRequestDto>> validationErrors = validator.validate(transactionRequestDtoFail);


        assertFalse(validationErrors.isEmpty(), "Es falso que el DTO no tiene errores de validación ");
        assertTrue(validationErrors.stream().anyMatch(
                ve -> ve.getMessage().equals("El Monto en un numero positivo"))
        );
        assertTrue(validationErrors.stream().anyMatch(
                ve -> ve.getMessage().equals("La clave tiene 16 dígitos"))
        );
    }
}
