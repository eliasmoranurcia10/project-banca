package com.apibancario.model.entity;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class ClienteTest {
    private Cliente cliente;

    @Test
    void testClienteAllArgsConstructor() {
        cliente = new Cliente(
                1,
                "Juan",
                "Mendoza",
                "78561545",
                "juanmendoza@gmail.com",
                new ArrayList<>()
        );

        assertNotNull(cliente);
        assertEquals(1, cliente.getIdCliente());
        assertEquals("Juan", cliente.getNombre());
        assertEquals("Mendoza", cliente.getApellido());
        assertEquals("78561545", cliente.getDni());
        assertEquals("juanmendoza@gmail.com", cliente.getEmail());
    }


    @Test
    void testClienteGettersAndSetters() {
        cliente = new Cliente();
        cliente.setIdCliente(1);
        cliente.setNombre("Juan");
        cliente.setApellido("Mendoza");
        cliente.setDni("78561545");
        cliente.setEmail("juanmendoza@gmail.com");

        assertNotNull(cliente);
        assertEquals(1, cliente.getIdCliente());
        assertEquals("Juan", cliente.getNombre());
        assertEquals("Mendoza", cliente.getApellido());
        assertEquals("78561545", cliente.getDni());
        assertEquals("juanmendoza@gmail.com", cliente.getEmail());
    }

}
