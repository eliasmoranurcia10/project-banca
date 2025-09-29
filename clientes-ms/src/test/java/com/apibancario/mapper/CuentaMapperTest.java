package com.apibancario.mapper;

import com.apibancario.model.dto.cliente.ClientResponseDto;
import com.apibancario.model.dto.cuenta.AccountRequestDto;
import com.apibancario.model.dto.cuenta.AccountResponseDto;
import com.apibancario.model.dto.cuenta.PasswordRequestDto;
import com.apibancario.model.entity.Cliente;
import com.apibancario.model.entity.Cuenta;
import com.apibancario.model.enums.TipoCuenta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class CuentaMapperTest {

    private final CuentaMapper cuentaMapper = Mappers.getMapper(CuentaMapper.class);
    private final ClienteMapper clienteMapper = Mappers.getMapper(ClienteMapper.class);

    private Cuenta cuenta;
    private AccountRequestDto accountRequestDto;
    private AccountResponseDto accountResponseDto;
    private PasswordRequestDto passwordRequestDto;
    private Cliente cliente;


    @BeforeEach
    void setUp() {
        cliente = new Cliente(1,"Elias", "Moran", "75484848", "elias@gmail.com",new ArrayList<>());
        ClientResponseDto clientResponseDto = new ClientResponseDto(1,"Elias", "Moran","elias@gmail.com");

        cuenta = new Cuenta(1,"56445587889696", "AHORRO", "585898", new BigDecimal("40000.00"), cliente);
        accountRequestDto = new AccountRequestDto(TipoCuenta.AHORRO, "585898", new BigDecimal("40000.00"), 1);
        accountResponseDto = new AccountResponseDto(1,"56445587889696", TipoCuenta.AHORRO, clientResponseDto);
        passwordRequestDto = new PasswordRequestDto("585898", "050505");
    }

    @Test
    void testToCuentaNull() {
        Cuenta cuenta = cuentaMapper.toCuenta(null);
        assertNull(cuenta);
    }

    @Test
    void testToCuenta() {
        Cuenta cuenta = cuentaMapper.toCuenta(accountRequestDto);
        assertNotNull(cuenta);
        assertEquals("AHORRO", cuenta.getTipoCuenta());
    }

    @Test
    void testToAccountResponseDtoNull() {
        AccountResponseDto accountResponseDtoNull = cuentaMapper.toAccountResponseDto(null);
        assertNull(accountResponseDtoNull);
    }

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
