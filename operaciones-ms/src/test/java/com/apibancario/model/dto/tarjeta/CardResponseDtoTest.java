package com.apibancario.model.dto.tarjeta;

import com.apibancario.model.enums.TipoTarjeta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CardResponseDtoTest {

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
    }

    @Test
    void testCardResponseDtoGetter() {
        assertNotNull(cardResponseDto);
        assertEquals(1, cardResponseDto.cardId());
        assertEquals("5654585625865968", cardResponseDto.cardNumber());
        assertEquals("04/30", cardResponseDto.expirationDate());
    }
}
