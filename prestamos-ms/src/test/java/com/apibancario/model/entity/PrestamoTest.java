package com.apibancario.model.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class PrestamoTest {

    private Prestamo prestamo;

    @Test
    void testPrestamoAllArgsConstructor() {
        prestamo = new Prestamo(
                1,
                new BigDecimal("3000.00"),
                new BigDecimal("0.20"),
                12,
                new BigDecimal("300.00"),
                "APROBADO",
                1,
                new ArrayList<>()
        );

        assertNotNull(prestamo);
        assertEquals(1, prestamo.getIdPrestamo());
        assertEquals(new BigDecimal("3000.00"), prestamo.getMontoTotal());
        assertEquals(new BigDecimal("0.20"), prestamo.getTasaInteres());
        assertEquals(12, prestamo.getPlazoMeses());
        assertEquals(new BigDecimal("300.00"), prestamo.getCuotaMensual());
        assertEquals("APROBADO", prestamo.getEstado());
        assertEquals(1, prestamo.getIdCliente());
    }

    @Test
    void testPrestamoGettersAndSetters() {
        prestamo = new Prestamo();
        prestamo.setIdPrestamo(1);
        prestamo.setMontoTotal(new BigDecimal("3000.00"));
        prestamo.setTasaInteres(new BigDecimal("0.20"));
        prestamo.setPlazoMeses(12);
        prestamo.setCuotaMensual(new BigDecimal("300.00"));
        prestamo.setEstado("APROBADO");
        prestamo.setIdCliente(1);

        assertNotNull(prestamo);
        assertEquals(1, prestamo.getIdPrestamo());
        assertEquals(new BigDecimal("3000.00"), prestamo.getMontoTotal());
        assertEquals(new BigDecimal("0.20"), prestamo.getTasaInteres());
        assertEquals(12, prestamo.getPlazoMeses());
        assertEquals(new BigDecimal("300.00"), prestamo.getCuotaMensual());
        assertEquals("APROBADO", prestamo.getEstado());
        assertEquals(1, prestamo.getIdCliente());
    }

}
