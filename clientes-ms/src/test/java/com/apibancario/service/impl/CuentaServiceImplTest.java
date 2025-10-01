package com.apibancario.service.impl;

import com.apibancario.exception.BadRequestException;
import com.apibancario.exception.InternalServerErrorException;
import com.apibancario.exception.ResourceNotFoundException;
import com.apibancario.mapper.CuentaMapper;
import com.apibancario.model.dto.cliente.ClientResponseDto;
import com.apibancario.model.dto.cuenta.AccountRequestDto;
import com.apibancario.model.dto.cuenta.AccountResponseDto;
import com.apibancario.model.dto.cuenta.PasswordRequestDto;
import com.apibancario.model.entity.Cliente;
import com.apibancario.model.entity.Cuenta;
import com.apibancario.model.enums.TipoCuenta;
import com.apibancario.repository.ClienteRepository;
import com.apibancario.repository.CuentaRepository;
import com.apibancario.util.GeneradorUtil;
import com.apibancario.util.PasswordUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CuentaServiceImplTest {

    @Mock
    private CuentaRepository cuentaRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private CuentaMapper cuentaMapper;

    @InjectMocks
    private CuentaServiceImpl cuentaService;

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
        accountResponseDto = new AccountResponseDto(1,"56445587889696", TipoCuenta.AHORRO, new BigDecimal("15"), clientResponseDto);
        passwordRequestDto = new PasswordRequestDto("585898", "050505");
    }

    @Test
    void testListAll_Success(){
        when(cuentaRepository.findAll()).thenReturn(List.of(cuenta));
        when(cuentaMapper.toAccountsResponseDto(anyList())).thenReturn(List.of(accountResponseDto));

        List<AccountResponseDto> result = cuentaService.listAll();

        assertNotNull(result);
        assertEquals("56445587889696", result.getFirst().accountNumber());
        assertEquals(1, result.size());
        verify(cuentaRepository, times(1)).findAll();
    }

    @Test
    void testFindById_BadRequestExceptionZero() {
        BadRequestException ex = assertThrows(BadRequestException.class, () -> cuentaService.findById(0) );
        assertEquals("El id es incorrecto", ex.getMessage());
    }

    @Test
    void testFindById_BadRequestExceptionNull() {
        BadRequestException ex = assertThrows(BadRequestException.class, () -> cuentaService.findById(null) );
        assertEquals("El id es incorrecto", ex.getMessage());
    }

    @Test
    void testFindById_ResourceNotFoundException() {
        Integer id = 99;
        when(cuentaRepository.findById(id)).thenReturn(Optional.empty());
        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () -> cuentaService.findById(id));
        assertEquals("No se encontró la cuenta con id: "+id, ex.getMessage());
    }

    @Test
    void testFindById_Success() {
        Integer id = 1;
        when(cuentaRepository.findById(id)).thenReturn(Optional.of(cuenta));
        when(cuentaMapper.toAccountResponseDto(cuenta)).thenReturn(accountResponseDto);

        AccountResponseDto result = cuentaService.findById(id);

        assertNotNull(result);
        assertEquals(1, result.accountId());
        assertEquals("56445587889696", result.accountNumber());
        verify(cuentaRepository, times(1)).findById(id);
    }

    @Test
    void testSave_InternalServerErrorException() {
        when(cuentaMapper.toCuenta(accountRequestDto)).thenReturn(cuenta);

        try (MockedStatic<GeneradorUtil> utilMock = Mockito.mockStatic(GeneradorUtil.class)) {

            utilMock.when(() -> GeneradorUtil.generarNumeroAleatorio(14))
                    .thenReturn("56445587889696");

            when(cuentaRepository.findByNumeroCuenta("56445587889696"))
                    .thenReturn(Optional.of(new Cuenta()))
                    .thenReturn(Optional.of(new Cuenta()))
                    .thenReturn(Optional.of(new Cuenta()));

            InternalServerErrorException ex = assertThrows(
                    InternalServerErrorException.class,
                    () -> cuentaService.save(accountRequestDto)
            );

            assertEquals("No fue posible generar un número de cuenta único", ex.getMessage());
        }
    }

    @Test
    void testSave_ResourceNotFoundExceptionClient() {
        when(cuentaMapper.toCuenta(accountRequestDto)).thenReturn(cuenta);
        when(clienteRepository.findById(accountRequestDto.clientId())).thenReturn(Optional.empty());

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () -> cuentaService.save(accountRequestDto));
        assertEquals("No se encontró el id del cliente", ex.getMessage());
    }

    @Test
    void testSave_BadRequestException() {
        when(cuentaMapper.toCuenta(accountRequestDto)).thenReturn(cuenta);
        when(clienteRepository.findById(accountRequestDto.clientId())).thenReturn(Optional.of(cliente));
        when(cuentaRepository.save(cuenta)).thenThrow(new RuntimeException("DB error"));

        BadRequestException ex = assertThrows(BadRequestException.class, () -> cuentaService.save(accountRequestDto));

        assertEquals("Error al crear nueva cuenta, ingresar datos correctos", ex.getMessage());
    }

    @Test
    void testSave_SuccessAccount() {
        when(cuentaMapper.toCuenta(accountRequestDto)).thenReturn(cuenta);
        when(clienteRepository.findById(accountRequestDto.clientId())).thenReturn(Optional.of(cliente));
        when(cuentaRepository.save(cuenta)).thenReturn(cuenta);
        when(cuentaMapper.toAccountResponseDto(cuenta)).thenReturn(accountResponseDto);

        AccountResponseDto result = cuentaService.save(accountRequestDto);

        assertNotNull(result);
        assertEquals(TipoCuenta.AHORRO, result.accountType());
        verify(cuentaRepository, times(1)).save(cuenta);
    }

    @Test
    void testUpdatePassword_BadRequestExceptionIdCuentaZero() {
        BadRequestException ex = assertThrows(BadRequestException.class, () -> cuentaService.updatePassword(0, passwordRequestDto));
        assertEquals("El id es incorrecto", ex.getMessage());
    }

    @Test
    void testUpdatePassword_BadRequestExceptionIdCuentaNull() {
        BadRequestException ex = assertThrows(BadRequestException.class, () -> cuentaService.updatePassword(null, passwordRequestDto));
        assertEquals("El id es incorrecto", ex.getMessage());
    }

    @Test
    void testUpdatePassword_ResourceNotFoundExceptionIdCuenta() {
        Integer id = 99;
        when(cuentaRepository.findById(id)).thenReturn(Optional.empty());
        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () -> cuentaService.updatePassword(id, passwordRequestDto));
        assertEquals("No se encontró la cuenta con id: "+id, ex.getMessage());
    }

    @Test
    void testUpdatePassword_BadRequestExceptionInvalidCredential() {
        when(cuentaRepository.findById(1)).thenReturn(Optional.of(cuenta));
        try (MockedStatic<PasswordUtil> utilMock = Mockito.mockStatic(PasswordUtil.class)) {
            utilMock.when(() -> PasswordUtil.matches(passwordRequestDto.oldPassword(), cuenta.getClaveAcceso()))
                    .thenReturn(false);


            BadRequestException ex = assertThrows(BadRequestException.class, () -> cuentaService.updatePassword(1, passwordRequestDto));
            assertEquals("Credenciales inválidas", ex.getMessage());
        }
    }

    @Test
    void testUpdatePassword_InternalServerErrorException() {
        when(cuentaRepository.findById(1)).thenReturn(Optional.of(cuenta));
        try (MockedStatic<PasswordUtil> utilMock = Mockito.mockStatic(PasswordUtil.class)) {
            utilMock.when(() -> PasswordUtil.matches(passwordRequestDto.oldPassword(), cuenta.getClaveAcceso()))
                    .thenReturn(true);

            utilMock.when(() -> PasswordUtil.hashPassword(passwordRequestDto.newPassword()))
                    .thenReturn("hashedNewPass");

            when(cuentaRepository.save(any(Cuenta.class)))
                    .thenThrow(new RuntimeException("DB error"));

            InternalServerErrorException ex = assertThrows(
                    InternalServerErrorException.class,
                    () -> cuentaService.updatePassword(1, passwordRequestDto)
            );

            assertEquals("Error al actualizar la clave de acceso", ex.getMessage());
        }
    }

    @Test
    void testDelete_BadRequestExceptionZero() {
        BadRequestException ex = assertThrows(BadRequestException.class, () -> cuentaService.delete(0));
        assertEquals("El id es incorrecto", ex.getMessage());
    }

    @Test
    void testDelete_BadRequestExceptionNull() {
        BadRequestException ex = assertThrows(BadRequestException.class, () -> cuentaService.delete(null));
        assertEquals("El id es incorrecto", ex.getMessage());
    }

    @Test
    void testDelete_ResourceNotFoundException() {
        Integer id = 99;
        when(cuentaRepository.findById(id)).thenReturn(Optional.empty());
        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () -> cuentaService.delete(id));
        assertEquals("No se encontró la cuenta con id: "+id, ex.getMessage());
    }

    @Test
    void testDelete_InternalServerErrorException() {
        when(cuentaRepository.findById(1)).thenReturn(Optional.of(cuenta));
        doThrow(new RuntimeException("DB error")).when(cuentaRepository).delete(cuenta);

        InternalServerErrorException ex = assertThrows(InternalServerErrorException.class, () -> cuentaService.delete(1));
        assertEquals("Error inesperado al eliminar la cuenta", ex.getMessage());
    }

    @Test
    void testDelete_Success() {
        when(cuentaRepository.findById(1)).thenReturn(Optional.of(cuenta));
        doNothing().when(cuentaRepository).delete(cuenta);

        cuentaService.delete(1);

        verify(cuentaRepository, times(1)).delete(cuenta);

    }

}
