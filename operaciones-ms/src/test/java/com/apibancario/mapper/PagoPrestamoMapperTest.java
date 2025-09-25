package com.apibancario.mapper;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class PagoPrestamoMapperTest {

    private final PagoPrestamoMapper pagoPrestamoMapper = Mappers.getMapper(PagoPrestamoMapper.class);

    @Test
    void testFechaPagoToPaymentDate_CaseNull() {
        String fechaPago = pagoPrestamoMapper.fechaPagoToPaymentDate(null);
        assertNull(fechaPago);
    }

    @Test
    void testFechaPagoToPaymentDate_CaseDateTime() {
        LocalDateTime localDateTime = LocalDateTime.of(2025,10,5,5,4,48);
        String fechaPago = pagoPrestamoMapper.fechaPagoToPaymentDate(localDateTime);
        assertNotNull(fechaPago);
        assertEquals("05/10/2025 05:04:48", fechaPago);
    }
}
