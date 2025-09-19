package com.apibancario.project_banca.model.dto.cuenta;

import com.apibancario.project_banca.model.dto.cliente.ClientResponseDto;
import com.apibancario.project_banca.model.enums.TipoCuenta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AccountResponseDtoTest {

    private AccountResponseDto accountResponseDto;
    private ClientResponseDto clientResponseDto;

    @BeforeEach
    void setUp() {

        clientResponseDto = new ClientResponseDto(
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
    }

    @Test
    void testAccountResponseDtoGetter() {
        assertNotNull(accountResponseDto);
        assertEquals(1, accountResponseDto.accountId());
        assertEquals("45455858595652", accountResponseDto.accountNumber());
        assertEquals(TipoCuenta.AHORRO, accountResponseDto.accountType());
        assertEquals(clientResponseDto, accountResponseDto.clientResponseDto());
    }

}
