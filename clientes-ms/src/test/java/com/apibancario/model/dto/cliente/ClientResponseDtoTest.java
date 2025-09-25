package com.apibancario.model.dto.cliente;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ClientResponseDtoTest {

    private ClientResponseDto clientResponseDto;

    @BeforeEach
    void setUp() {
        clientResponseDto = new ClientResponseDto(
                1,
                "Juan",
                "Pérez",
                "juan.perez@mail.com"
        );
    }

    @Test
    void testClientResponseDtoGetter() {
        assertNotNull(clientResponseDto);
        assertEquals(1, clientResponseDto.clientId());
        assertEquals("Juan", clientResponseDto.name());
        assertEquals("Pérez", clientResponseDto.lastName());
        assertEquals("juan.perez@mail.com", clientResponseDto.email());
    }

}
