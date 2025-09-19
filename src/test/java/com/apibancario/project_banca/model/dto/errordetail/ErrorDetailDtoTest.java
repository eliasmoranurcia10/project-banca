package com.apibancario.project_banca.model.dto.errordetail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ErrorDetailDtoTest {

    private ErrorDetailDto errorDetailDto;

    @BeforeEach
    void setUp() {
        errorDetailDto = new ErrorDetailDto("bad-request", "Recurso inválido", "2025-09-18 20:30:15");
    }

    @Test
    void testErrorDetailDtoGetter() {
        assertNotNull(errorDetailDto);
        assertEquals("bad-request", errorDetailDto.type());
        assertEquals("Recurso inválido", errorDetailDto.message());
        assertEquals("2025-09-18 20:30:15", errorDetailDto.dateTime());
    }
}
