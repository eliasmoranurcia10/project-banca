package com.apibancario.project_banca.mapper;

import com.apibancario.project_banca.model.enums.TipoCuenta;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

public class CuentaMapperTest {

    private final CuentaMapper cuentaMapper = Mappers.getMapper(CuentaMapper.class);

    @Test
    void testTipoCuentaToAccountType_CaseNull() {
        TipoCuenta tipoCuenta = cuentaMapper.tipoCuentaToAccountType(null);
        assertNull(tipoCuenta);
    }

    @Test
    void testTipoCuentaToAccountType_CaseAhorro() {
        TipoCuenta tipoCuenta = cuentaMapper.tipoCuentaToAccountType("AHORRO");
        assertNotNull(tipoCuenta);
        assertEquals(TipoCuenta.AHORRO, tipoCuenta);
    }

    @Test
    void testTipoCuentaToAccountType_CaseCorriente() {
        TipoCuenta tipoCuenta = cuentaMapper.tipoCuentaToAccountType("Corriente");
        assertNotNull(tipoCuenta);
        assertEquals(TipoCuenta.CORRIENTE, tipoCuenta);
    }

    @Test
    void testTipoCuentaToAccountType_CaseDefault() {
        TipoCuenta tipoCuenta = cuentaMapper.tipoCuentaToAccountType("Cualquier_Valor");
        assertNull(tipoCuenta);
    }
}
