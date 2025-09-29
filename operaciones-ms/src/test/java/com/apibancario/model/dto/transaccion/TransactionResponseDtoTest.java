package com.apibancario.model.dto.transaccion;

import com.apibancario.model.dto.tarjeta.CardResponseDto;
import com.apibancario.model.enums.TipoTarjeta;
import com.apibancario.model.enums.TipoTransaccion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class TransactionResponseDtoTest {

    private TransactionResponseDto transactionResponseDto;
    private CardResponseDto cardResponseDto;

    @BeforeEach
    void setUp() {

        cardResponseDto = new CardResponseDto(
                1,
                "5654585625865968",
                TipoTarjeta.DEBITO,
                "04/30",
                1
        );

        transactionResponseDto = new TransactionResponseDto(
                1,
                TipoTransaccion.DEPOSITO,
                new BigDecimal("550.00"),
                "10/08/2025 09:05:16",
                cardResponseDto,
                null
        );
    }

    @Test
    void testTransactionResponseDtoGetter() {
        assertNotNull(transactionResponseDto);
        assertEquals(1, transactionResponseDto.transactionId());
        assertEquals(TipoTransaccion.DEPOSITO, transactionResponseDto.transactionType());
        assertEquals(new BigDecimal("550.00"), transactionResponseDto.amount());
        assertEquals(cardResponseDto, transactionResponseDto.cardResponseDto());
        assertNull(transactionResponseDto.RecipientAccountId());
    }
}
