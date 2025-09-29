package com.apibancario.model.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class CuentaTest {

    private Cuenta cuenta;
    private Cliente cliente;

    @BeforeEach
    void setUp() {
        cliente = new Cliente(
                1,
                "Juan",
                "Mendoza",
                "78561545",
                "juanmendoza@gmail.com",
                new ArrayList<>()
        );
    }


    @Test
    void testCuentaAllArgsConstructor() {
        cuenta = new Cuenta(
                1,
                "54465265636895",
                "AHORRO",
                "455856",
                new BigDecimal("10.50"),
                cliente
        );

        assertNotNull(cuenta);
        assertEquals(1, cuenta.getIdCuenta());
        assertEquals("54465265636895", cuenta.getNumeroCuenta());
        assertEquals("AHORRO", cuenta.getTipoCuenta());
        assertEquals("455856", cuenta.getClaveAcceso());
        assertEquals(new BigDecimal("10.50"), cuenta.getSaldo());
        assertEquals(cliente, cuenta.getCliente());

    }

    @Test
    void testCuentaGettersAndSetters() {
        cuenta = new Cuenta();
        cuenta.setIdCuenta(1);
        cuenta.setNumeroCuenta("54465265636895");
        cuenta.setTipoCuenta("AHORRO");
        cuenta.setClaveAcceso("455856");
        cuenta.setSaldo(new BigDecimal("10.50"));
        cuenta.setCliente(cliente);

        assertNotNull(cuenta);
        assertEquals(1, cuenta.getIdCuenta());
        assertEquals("54465265636895", cuenta.getNumeroCuenta());
        assertEquals("AHORRO", cuenta.getTipoCuenta());
        assertEquals("455856", cuenta.getClaveAcceso());
        assertEquals(new BigDecimal("10.50"), cuenta.getSaldo());
        assertEquals(cliente, cuenta.getCliente());
    }

}
