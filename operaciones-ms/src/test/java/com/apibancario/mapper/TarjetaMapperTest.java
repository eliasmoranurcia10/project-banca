package com.apibancario.mapper;

import com.apibancario.model.enums.TipoTarjeta;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

public class TarjetaMapperTest {

    private final TarjetaMapper tarjetaMapper = Mappers.getMapper(TarjetaMapper.class);

    @Test
    void testTipoTarjetaToCardType_CaseNull() {
        TipoTarjeta tipoTarjeta = tarjetaMapper.tipoTarjetaToCardType(null);
        assertNull(tipoTarjeta);
    }

    @Test
    void testTipoTarjetaToCardType_CaseDefault() {
        TipoTarjeta tipoTarjeta = tarjetaMapper.tipoTarjetaToCardType("CualquierValor");
        assertNull(tipoTarjeta);
    }

    @Test
    void testTipoTarjetaToCardType_CaseDebito() {
        TipoTarjeta tipoTarjeta = tarjetaMapper.tipoTarjetaToCardType("DEBITO");
        assertNotNull(tipoTarjeta);
        assertEquals(TipoTarjeta.DEBITO, tipoTarjeta);
    }

    @Test
    void testTipoTarjetaToCardType_CaseCredito() {
        TipoTarjeta tipoTarjeta = tarjetaMapper.tipoTarjetaToCardType("CREDITO");
        assertNotNull(tipoTarjeta);
        assertEquals(TipoTarjeta.CREDITO, tipoTarjeta);
    }
}
