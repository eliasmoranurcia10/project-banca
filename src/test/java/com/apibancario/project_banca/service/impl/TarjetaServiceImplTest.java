package com.apibancario.project_banca.service.impl;

import com.apibancario.project_banca.exception.BadRequestException;
import com.apibancario.project_banca.exception.InternalServerErrorException;
import com.apibancario.project_banca.exception.ResourceNotFoundException;
import com.apibancario.project_banca.mapper.TarjetaMapper;
import com.apibancario.project_banca.model.dto.cliente.ClientResponseDto;
import com.apibancario.project_banca.model.dto.cuenta.AccountResponseDto;
import com.apibancario.project_banca.model.dto.tarjeta.CardPinRequestDto;
import com.apibancario.project_banca.model.dto.tarjeta.CardRequestDto;
import com.apibancario.project_banca.model.dto.tarjeta.CardResponseDto;
import com.apibancario.project_banca.model.entity.Cliente;
import com.apibancario.project_banca.model.entity.Cuenta;
import com.apibancario.project_banca.model.entity.Tarjeta;
import com.apibancario.project_banca.model.enums.TipoCuenta;
import com.apibancario.project_banca.model.enums.TipoTarjeta;
import com.apibancario.project_banca.repository.CuentaRepository;
import com.apibancario.project_banca.repository.TarjetaRepository;
import com.apibancario.project_banca.util.GeneradorUtil;
import com.apibancario.project_banca.util.PasswordUtil;
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
public class TarjetaServiceImplTest {
    @Mock
    private TarjetaRepository tarjetaRepository;

    @Mock
    private CuentaRepository cuentaRepository;

    @Mock
    private TarjetaMapper tarjetaMapper;

    @InjectMocks
    private TarjetaServiceImpl tarjetaService;

    private Tarjeta tarjeta;
    private CardRequestDto cardRequestDto;
    private CardResponseDto cardResponseDto;
    private CardPinRequestDto cardPinRequestDto;
    private Cuenta cuenta;

    @BeforeEach
    void setUp() {
        Cliente cliente = new Cliente(1,"Elias", "Moran", "75484848", "elias@gmail.com",new ArrayList<>(), new ArrayList<>());
        ClientResponseDto clientResponseDto = new ClientResponseDto(1,"Elias", "Moran","elias@gmail.com");

        cuenta = new Cuenta(1,"56445587889696", "AHORRO", "585898", new BigDecimal("40000.00"), cliente, new ArrayList<>(), new ArrayList<>());
        AccountResponseDto accountResponseDto = new AccountResponseDto(1,"56445587889696", TipoCuenta.AHORRO, clientResponseDto);

        tarjeta = new Tarjeta(1, "4552366895916954", "DEBITO", "09/30", "5058", "548", cuenta, new ArrayList<>());
        cardRequestDto = new CardRequestDto(TipoTarjeta.DEBITO,"5058", 1);
        cardResponseDto = new CardResponseDto(1, "4552366895916954", TipoTarjeta.DEBITO, "09/30", accountResponseDto);
        cardPinRequestDto = new CardPinRequestDto("5058","0303");
    }

    @Test
    void testListAll_Success() {
        when(tarjetaRepository.findAll()).thenReturn(List.of(tarjeta));
        when(tarjetaMapper.toCardsResponseDto(anyList())).thenReturn(List.of(cardResponseDto));

        List<CardResponseDto> results = tarjetaService.listAll();

        assertNotNull(results);
        assertEquals("4552366895916954", results.getFirst().cardNumber());
        assertEquals(1, results.size());
        verify(tarjetaRepository, times(1)).findAll();
    }

    @Test
    void testFindById_BadRequestExceptionZero() {
        BadRequestException ex = assertThrows(BadRequestException.class, () -> tarjetaService.findById(0) );
        assertEquals("El id es incorrecto", ex.getMessage());
    }

    @Test
    void testFindById_BadRequestExceptionNull() {
        BadRequestException ex = assertThrows(BadRequestException.class, () -> tarjetaService.findById(null) );
        assertEquals("El id es incorrecto", ex.getMessage());
    }

    @Test
    void testFindById_ResourceNotFoundException() {
        Integer id = 99;
        when(tarjetaRepository.findById(id)).thenReturn(Optional.empty());
        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () -> tarjetaService.findById(id));
        assertEquals("No se encontró la tarjeta con id: "+id, ex.getMessage());
    }

    @Test
    void testFindById_Success() {
        Integer id = 1;
        when(tarjetaRepository.findById(id)).thenReturn(Optional.of(tarjeta));
        when(tarjetaMapper.toCardResponseDto(tarjeta)).thenReturn(cardResponseDto);

        CardResponseDto result = tarjetaService.findById(id);

        assertNotNull(result);
        assertEquals(1, result.cardId());
        assertEquals("4552366895916954", result.cardNumber());
        verify(tarjetaRepository, times(1)).findById(id);
    }

    @Test
    void testSave_InternalServerErrorException() {
        when(tarjetaMapper.toTarjeta(cardRequestDto)).thenReturn(tarjeta);

        try (MockedStatic<GeneradorUtil> utilMock = Mockito.mockStatic(GeneradorUtil.class)) {

            utilMock.when(() -> GeneradorUtil.generarNumeroAleatorio(16))
                    .thenReturn("4552366895916954");

            when(tarjetaRepository.findByNumeroTarjeta("4552366895916954"))
                    .thenReturn(Optional.of(new Tarjeta()))
                    .thenReturn(Optional.of(new Tarjeta()))
                    .thenReturn(Optional.of(new Tarjeta()));

            InternalServerErrorException ex = assertThrows(
                    InternalServerErrorException.class,
                    () -> tarjetaService.save(cardRequestDto)
            );

            assertEquals("No fue posible generar un número de tarjeta único", ex.getMessage());
        }
    }

    @Test
    void testSave_ResourceNotFoundExceptionAccount() {
        when(tarjetaMapper.toTarjeta(cardRequestDto)).thenReturn(tarjeta);
        when(cuentaRepository.findById(cardRequestDto.accountId())).thenReturn(Optional.empty());

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () -> tarjetaService.save(cardRequestDto));
        assertEquals("No se encontró el id de la cuenta. id: "+ cardRequestDto.accountId(), ex.getMessage());
    }

    @Test
    void testSave_BadRequestException() {
        when(tarjetaMapper.toTarjeta(cardRequestDto)).thenReturn(tarjeta);
        when(cuentaRepository.findById(cardRequestDto.accountId())).thenReturn(Optional.of(cuenta));
        when(tarjetaRepository.save(tarjeta)).thenThrow(new RuntimeException("DB error"));

        BadRequestException ex = assertThrows(BadRequestException.class, () -> tarjetaService.save(cardRequestDto));

        assertEquals("Error al crear nueva tarjeta, ingrese los datos correctos", ex.getMessage());
    }

    @Test
    void testSave_SuccessCard() {
        when(tarjetaMapper.toTarjeta(cardRequestDto)).thenReturn(tarjeta);
        when(cuentaRepository.findById(cardRequestDto.accountId())).thenReturn(Optional.of(cuenta));
        when(tarjetaRepository.save(tarjeta)).thenReturn(tarjeta);
        when(tarjetaMapper.toCardResponseDto(tarjeta)).thenReturn(cardResponseDto);

        CardResponseDto result = tarjetaService.save(cardRequestDto);

        assertNotNull(result);
        assertEquals(TipoTarjeta.DEBITO, result.cardType());
        verify(tarjetaRepository, times(1)).save(tarjeta);
    }

    @Test
    void testUpdatePinTarjeta_BadRequestExceptionIdCuentaZero() {
        BadRequestException ex = assertThrows(BadRequestException.class, () -> tarjetaService.updatePinTarjeta(0, cardPinRequestDto));
        assertEquals("El id es incorrecto", ex.getMessage());
    }

    @Test
    void testUpdatePinTarjeta_BadRequestExceptionIdCuentaNull() {
        BadRequestException ex = assertThrows(BadRequestException.class, () -> tarjetaService.updatePinTarjeta(null, cardPinRequestDto));
        assertEquals("El id es incorrecto", ex.getMessage());
    }

    @Test
    void testUpdatePinTarjeta_ResourceNotFoundExceptionIdCuenta() {
        Integer id = 99;
        when(tarjetaRepository.findById(id)).thenReturn(Optional.empty());
        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () -> tarjetaService.updatePinTarjeta(id, cardPinRequestDto));
        assertEquals("No se encontró la tarjeta con id: "+id, ex.getMessage());
    }

    @Test
    void testUpdatePinTarjeta_BadRequestExceptionInvalidCredential() {
        when(tarjetaRepository.findById(1)).thenReturn(Optional.of(tarjeta));

        try (MockedStatic<PasswordUtil> utilMock = Mockito.mockStatic(PasswordUtil.class)) {
            utilMock.when(() -> PasswordUtil.matches(cardPinRequestDto.oldPin(), tarjeta.getPinTarjeta()))
                    .thenReturn(false);


            BadRequestException ex = assertThrows(BadRequestException.class, () -> tarjetaService.updatePinTarjeta(1, cardPinRequestDto));
            assertEquals("Credenciales inválidas", ex.getMessage());
        }
    }

    @Test
    void testUpdatePinTarjeta_InternalServerErrorException() {
        when(tarjetaRepository.findById(1)).thenReturn(Optional.of(tarjeta));
        try (MockedStatic<PasswordUtil> utilMock = Mockito.mockStatic(PasswordUtil.class)) {
            utilMock.when(() -> PasswordUtil.matches(cardPinRequestDto.oldPin(), tarjeta.getPinTarjeta()))
                    .thenReturn(true);

            utilMock.when(() -> PasswordUtil.hashPassword(cardPinRequestDto.newPin()))
                    .thenReturn("03031");

            when(tarjetaRepository.save(any(Tarjeta.class)))
                    .thenThrow(new RuntimeException("DB error"));

            InternalServerErrorException ex = assertThrows(
                    InternalServerErrorException.class,
                    () -> tarjetaService.updatePinTarjeta(1, cardPinRequestDto)
            );

            assertEquals("Error al actualizar el pin de la tarjeta", ex.getMessage());
        }
    }

    @Test
    void testDelete_BadRequestExceptionZero() {
        BadRequestException ex = assertThrows(BadRequestException.class, () -> tarjetaService.delete(0));
        assertEquals("El id es incorrecto", ex.getMessage());
    }

    @Test
    void testDelete_BadRequestExceptionNull() {
        BadRequestException ex = assertThrows(BadRequestException.class, () -> tarjetaService.delete(null));
        assertEquals("El id es incorrecto", ex.getMessage());
    }

    @Test
    void testDelete_ResourceNotFoundException() {
        Integer id = 99;
        when(tarjetaRepository.findById(id)).thenReturn(Optional.empty());
        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () -> tarjetaService.delete(id));
        assertEquals("No se encontró la tarjeta con id: "+id, ex.getMessage());
    }

    @Test
    void testDelete_InternalServerErrorException() {
        when(tarjetaRepository.findById(1)).thenReturn(Optional.of(tarjeta));
        doThrow(new RuntimeException("DB error")).when(tarjetaRepository).delete(tarjeta);

        InternalServerErrorException ex = assertThrows(InternalServerErrorException.class, () -> tarjetaService.delete(1));
        assertEquals("Error inesperado al eliminar la tarjeta", ex.getMessage());
    }

    @Test
    void testDelete_Success() {
        when(tarjetaRepository.findById(1)).thenReturn(Optional.of(tarjeta));
        doNothing().when(tarjetaRepository).delete(tarjeta);

        tarjetaService.delete(1);

        verify(tarjetaRepository, times(1)).delete(tarjeta);

    }
}
