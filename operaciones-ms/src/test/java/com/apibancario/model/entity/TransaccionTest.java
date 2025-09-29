package com.apibancario.model.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TransaccionTest {

    private Transaccion transaccion;
    private Tarjeta tarjeta;

    @BeforeEach
    void setUp() {

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
    }

    @Test
    void testTransaccionAllArgsConstructor() {
        transaccion = new Transaccion(
                1,
                "DEPOSITO",
                new BigDecimal("30.00"),
                LocalDateTime.of(2025, 9, 18, 10, 30, 0),
                tarjeta,
                null
        );

        assertNotNull(transaccion);
        assertEquals(1, transaccion.getIdTransaccion());
        assertEquals("DEPOSITO", transaccion.getTipoTransaccion());
        assertEquals(new BigDecimal("30.00"), transaccion.getMonto());
        assertEquals(LocalDateTime.of(2025, 9, 18, 10, 30, 0), transaccion.getFecha());
        assertEquals(tarjeta, transaccion.getTarjeta());
        assertNull(transaccion.getIdCuentaDestino());

    }

    @Test
    void testTarjetaGettersAndSetters() {
        transaccion = new Transaccion();
        transaccion.setIdTransaccion(1);
        transaccion.setTipoTransaccion("DEPOSITO");
        transaccion.setMonto(new BigDecimal("30.00"));
        transaccion.setFecha(LocalDateTime.of(2025, 9, 18, 10, 30, 0));
        transaccion.setTarjeta(tarjeta);
        transaccion.setIdCuentaDestino(null);

        assertNotNull(transaccion);
        assertEquals(1, transaccion.getIdTransaccion());
        assertEquals("DEPOSITO", transaccion.getTipoTransaccion());
        assertEquals(new BigDecimal("30.00"), transaccion.getMonto());
        assertEquals(LocalDateTime.of(2025, 9, 18, 10, 30, 0), transaccion.getFecha());
        assertEquals(tarjeta, transaccion.getTarjeta());
        assertNull(transaccion.getIdCuentaDestino());
    }
}
