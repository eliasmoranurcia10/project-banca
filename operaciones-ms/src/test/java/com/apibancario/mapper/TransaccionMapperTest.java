package com.apibancario.mapper;

import com.apibancario.model.enums.TipoTransaccion;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class TransaccionMapperTest {

    private final TransaccionMapper transaccionMapper = Mappers.getMapper(TransaccionMapper.class);

    @Test
    void testFechaToDate_CaseNull() {
        String fecha = transaccionMapper.fechaToDate(null);
        assertNull(fecha);
    }

    @Test
    void testFechaToDate_CaseLocalDateTime() {
        LocalDateTime localDateTime = LocalDateTime.of(2025,12,6,7,4,48);
        String fecha = transaccionMapper.fechaToDate(localDateTime);
        assertNotNull(fecha);
        assertEquals("06/12/2025 07:04:48", fecha);
    }

    @Test
    void testTipoTransaccionToTransactionType_CaseNull() {
        TipoTransaccion tipoTransaccion = transaccionMapper.tipoTransaccionToTransactionType(null);
        assertNull(tipoTransaccion);
    }

    @Test
    void testTipoTransaccionToTransactionType_CaseDefault() {
        TipoTransaccion tipoTransaccion = transaccionMapper.tipoTransaccionToTransactionType("CualquierValor");
        assertNull(tipoTransaccion);
    }

    @Test
    void testTipoTransaccionToTransactionType_CaseDeposito() {
        TipoTransaccion tipoTransaccion = transaccionMapper.tipoTransaccionToTransactionType("Deposito");
        assertNotNull(tipoTransaccion);
        assertEquals(TipoTransaccion.DEPOSITO, tipoTransaccion);
    }

    @Test
    void testTipoTransaccionToTransactionType_CaseRetiro() {
        TipoTransaccion tipoTransaccion = transaccionMapper.tipoTransaccionToTransactionType("RETIRO");
        assertNotNull(tipoTransaccion);
        assertEquals(TipoTransaccion.RETIRO, tipoTransaccion);
    }

    @Test
    void testTipoTransaccionToTransactionType_CaseTransferencia() {
        TipoTransaccion tipoTransaccion = transaccionMapper.tipoTransaccionToTransactionType("TRANSFERENCIA");
        assertNotNull(tipoTransaccion);
        assertEquals(TipoTransaccion.TRANSFERENCIA, tipoTransaccion);
    }

    @Test
    void testTipoTransaccionToTransactionType_CasePago() {
        TipoTransaccion tipoTransaccion = transaccionMapper.tipoTransaccionToTransactionType("PAGO");
        assertNotNull(tipoTransaccion);
        assertEquals(TipoTransaccion.PAGO, tipoTransaccion);
    }
}
