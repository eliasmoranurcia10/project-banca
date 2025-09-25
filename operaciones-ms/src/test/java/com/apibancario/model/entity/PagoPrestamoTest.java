package com.apibancario.model.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class PagoPrestamoTest {

    private PagoPrestamo pagoPrestamo;
    private Prestamo prestamo;

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
    }

    @Test
    void testPagoPrestamoAllArgsConstructor() {
        pagoPrestamo = new PagoPrestamo(
                1,
                new BigDecimal("600"),
                LocalDateTime.of(2025, 9, 18, 10, 30, 0),
                prestamo
        );

        assertNotNull(pagoPrestamo);
        assertEquals(1, pagoPrestamo.getIdPago());
        assertEquals(new BigDecimal("600"), pagoPrestamo.getMontoPago());
        assertEquals(LocalDateTime.of(2025, 9, 18, 10, 30, 0), pagoPrestamo.getFechaPago());
        assertEquals(prestamo, pagoPrestamo.getPrestamo());
    }

    @Test
    void testPagoPrestamoGettersAndSetters() {
        pagoPrestamo = new PagoPrestamo();
        pagoPrestamo.setIdPago(1);
        pagoPrestamo.setMontoPago(new BigDecimal("600"));
        pagoPrestamo.setFechaPago(LocalDateTime.of(2025, 9, 18, 10, 30, 0));
        pagoPrestamo.setPrestamo(prestamo);

        assertNotNull(pagoPrestamo);
        assertEquals(1, pagoPrestamo.getIdPago());
        assertEquals(new BigDecimal("600"), pagoPrestamo.getMontoPago());
        assertEquals(LocalDateTime.of(2025, 9, 18, 10, 30, 0), pagoPrestamo.getFechaPago());
        assertEquals(prestamo, pagoPrestamo.getPrestamo());
    }
}
