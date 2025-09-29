package com.apibancario.model.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TarjetaTest {

    private Tarjeta tarjeta;

    @Test
    void testTarjetaAllArgsConstructor() {
        tarjeta = new Tarjeta(
                1,
                "5456252585562525",
                "DEBITO",
                "15/30",
                "2526",
                "548",
                1,
                new ArrayList<>()
        );

        assertNotNull(tarjeta);
        assertEquals(1, tarjeta.getIdTarjeta());
        assertEquals("5456252585562525", tarjeta.getNumeroTarjeta());
        assertEquals("DEBITO", tarjeta.getTipoTarjeta());
        assertEquals("15/30", tarjeta.getFechaVencimiento());
        assertEquals("2526", tarjeta.getPinTarjeta());
        assertEquals("548", tarjeta.getCvvTarjeta());
        assertEquals(1, tarjeta.getIdCuenta());
    }

    @Test
    void testTarjetaGettersAndSetters() {
        tarjeta = new Tarjeta();
        tarjeta.setIdTarjeta(1);
        tarjeta.setNumeroTarjeta("5456252585562525");
        tarjeta.setTipoTarjeta("DEBITO");
        tarjeta.setFechaVencimiento("15/30");
        tarjeta.setPinTarjeta("2526");
        tarjeta.setCvvTarjeta("548");
        tarjeta.setIdCuenta(1);

        assertNotNull(tarjeta);
        assertEquals(1, tarjeta.getIdTarjeta());
        assertEquals("5456252585562525", tarjeta.getNumeroTarjeta());
        assertEquals("DEBITO", tarjeta.getTipoTarjeta());
        assertEquals("15/30", tarjeta.getFechaVencimiento());
        assertEquals("2526", tarjeta.getPinTarjeta());
        assertEquals("548", tarjeta.getCvvTarjeta());
        assertEquals(1, tarjeta.getIdCuenta());
    }
}
