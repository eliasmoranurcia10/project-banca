package com.apibancario.project_banca.model.entity;

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
        Cliente cliente = new Cliente(
                1,
                "Juan",
                "Mendoza",
                "78561545",
                "juanmendoza@gmail.com",
                new ArrayList<>(),
                new ArrayList<>()
        );

        Cuenta cuenta = new Cuenta(
                1,
                "54465265636895",
                "AHORRO",
                "455856",
                new BigDecimal("10.50"),
                cliente,
                new ArrayList<>(),
                new ArrayList<>()
        );

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
        assertNull(transaccion.getCuentaDestino());

    }

    @Test
    void testTarjetaGettersAndSetters() {
        transaccion = new Transaccion();
        transaccion.setIdTransaccion(1);
        transaccion.setTipoTransaccion("DEPOSITO");
        transaccion.setMonto(new BigDecimal("30.00"));
        transaccion.setFecha(LocalDateTime.of(2025, 9, 18, 10, 30, 0));
        transaccion.setTarjeta(tarjeta);
        transaccion.setCuentaDestino(null);

        assertNotNull(transaccion);
        assertEquals(1, transaccion.getIdTransaccion());
        assertEquals("DEPOSITO", transaccion.getTipoTransaccion());
        assertEquals(new BigDecimal("30.00"), transaccion.getMonto());
        assertEquals(LocalDateTime.of(2025, 9, 18, 10, 30, 0), transaccion.getFecha());
        assertEquals(tarjeta, transaccion.getTarjeta());
        assertNull(transaccion.getCuentaDestino());
    }
}
