package com.apibancario.project_banca.model.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TarjetaTest {

    private Tarjeta tarjeta;
    private Cuenta cuenta;

    @BeforeEach
    void setUp() {

        Cliente cliente = new Cliente(
                1,
                "Juan",
                "Mendoza",
                "78561545",
                "juanmendoza@gmail.com",
                new ArrayList<>(),
                new ArrayList<>()
        );

        cuenta = new Cuenta(
                1,
                "54465265636895",
                "AHORRO",
                "455856",
                new BigDecimal("10.50"),
                cliente,
                new ArrayList<>(),
                new ArrayList<>()
        );
    }

    @Test
    void testTarjetaAllArgsConstructor() {
        tarjeta = new Tarjeta(
                1,
                "5456252585562525",
                "DEBITO",
                "15/30",
                "2526",
                "548",
                cuenta,
                new ArrayList<>()
        );

        assertNotNull(tarjeta);
        assertEquals(1, tarjeta.getIdTarjeta());
        assertEquals("5456252585562525", tarjeta.getNumeroTarjeta());
        assertEquals("DEBITO", tarjeta.getTipoTarjeta());
        assertEquals("15/30", tarjeta.getFechaVencimiento());
        assertEquals("2526", tarjeta.getPinTarjeta());
        assertEquals("548", tarjeta.getCvvTarjeta());
        assertEquals(cuenta, tarjeta.getCuenta());
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
        tarjeta.setCuenta(cuenta);

        assertNotNull(tarjeta);
        assertEquals(1, tarjeta.getIdTarjeta());
        assertEquals("5456252585562525", tarjeta.getNumeroTarjeta());
        assertEquals("DEBITO", tarjeta.getTipoTarjeta());
        assertEquals("15/30", tarjeta.getFechaVencimiento());
        assertEquals("2526", tarjeta.getPinTarjeta());
        assertEquals("548", tarjeta.getCvvTarjeta());
        assertEquals(cuenta, tarjeta.getCuenta());
    }
}
