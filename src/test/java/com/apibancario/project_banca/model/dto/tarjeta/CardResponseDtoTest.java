package com.apibancario.project_banca.model.dto.tarjeta;

import com.apibancario.project_banca.model.dto.cliente.ClientResponseDto;
import com.apibancario.project_banca.model.dto.cuenta.AccountResponseDto;
import com.apibancario.project_banca.model.enums.TipoCuenta;
import com.apibancario.project_banca.model.enums.TipoTarjeta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CardResponseDtoTest {

    private CardResponseDto cardResponseDto;
    private AccountResponseDto accountResponseDto;

    @BeforeEach
    void setUp() {

        ClientResponseDto clientResponseDto = new ClientResponseDto(
                1,
                "Juan",
                "Pérez",
                "juan.perez@mail.com"
        );

        accountResponseDto = new AccountResponseDto(
                1,
                "45455858595652",
                TipoCuenta.AHORRO,
                clientResponseDto
        );

        cardResponseDto = new CardResponseDto(
                1,
                "5654585625865968",
                TipoTarjeta.DEBITO,
                "04/30",
                accountResponseDto
        );
    }

    @Test
    void testCardResponseDtoGetter() {
        assertNotNull(cardResponseDto);
        assertEquals(1, cardResponseDto.cardId());
        assertEquals("5654585625865968", cardResponseDto.cardNumber());
        assertEquals("04/30", cardResponseDto.expirationDate());
        assertEquals(accountResponseDto, cardResponseDto.accountResponseDto());
    }
}
