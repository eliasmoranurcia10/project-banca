package com.apibancario.model.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class PrestamoTest {

    private Prestamo prestamo;
    private Cliente cliente;

    @BeforeEach
    void setUp() {
        cliente = new Cliente(
                1,
                "Juan",
                "Mendoza",
                "78561545",
                "juanmendoza@gmail.com",
                new ArrayList<>(),
                new ArrayList<>()
        );
    }

    @Test
    void testPrestamoAllArgsConstructor() {
        prestamo = new Prestamo(
                1,
                new BigDecimal("3000.00"),
                new BigDecimal("0.20"),
                12,
                new BigDecimal("300.00"),
                "APROBADO",
                cliente,
                new ArrayList<>()
        );

        assertNotNull(prestamo);
        assertEquals(1, prestamo.getIdPrestamo());
        assertEquals(new BigDecimal("3000.00"), prestamo.getMontoTotal());
        assertEquals(new BigDecimal("0.20"), prestamo.getTasaInteres());
        assertEquals(12, prestamo.getPlazoMeses());
        assertEquals(new BigDecimal("300.00"), prestamo.getCuotaMensual());
        assertEquals("APROBADO", prestamo.getEstado());
        assertEquals(cliente, prestamo.getCliente());
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
        prestamo.setCliente(cliente);

        assertNotNull(prestamo);
        assertEquals(1, prestamo.getIdPrestamo());
        assertEquals(new BigDecimal("3000.00"), prestamo.getMontoTotal());
        assertEquals(new BigDecimal("0.20"), prestamo.getTasaInteres());
        assertEquals(12, prestamo.getPlazoMeses());
        assertEquals(new BigDecimal("300.00"), prestamo.getCuotaMensual());
        assertEquals("APROBADO", prestamo.getEstado());
        assertEquals(cliente, prestamo.getCliente());
    }

}
