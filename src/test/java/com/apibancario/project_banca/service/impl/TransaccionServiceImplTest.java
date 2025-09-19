package com.apibancario.project_banca.service.impl;

import com.apibancario.project_banca.exception.BadRequestException;
import com.apibancario.project_banca.exception.InternalServerErrorException;
import com.apibancario.project_banca.exception.ResourceNotFoundException;
import com.apibancario.project_banca.mapper.TarjetaMapper;
import com.apibancario.project_banca.mapper.TransaccionMapper;
import com.apibancario.project_banca.model.dto.cliente.ClientResponseDto;
import com.apibancario.project_banca.model.dto.cuenta.AccountResponseDto;
import com.apibancario.project_banca.model.dto.tarjeta.CardResponseDto;
import com.apibancario.project_banca.model.dto.transaccion.TransactionRequestDto;
import com.apibancario.project_banca.model.dto.transaccion.TransactionResponseDto;
import com.apibancario.project_banca.model.entity.Cliente;
import com.apibancario.project_banca.model.entity.Cuenta;
import com.apibancario.project_banca.model.entity.Tarjeta;
import com.apibancario.project_banca.model.entity.Transaccion;
import com.apibancario.project_banca.model.enums.TipoCuenta;
import com.apibancario.project_banca.model.enums.TipoTarjeta;
import com.apibancario.project_banca.model.enums.TipoTransaccion;
import com.apibancario.project_banca.repository.CuentaRepository;
import com.apibancario.project_banca.repository.TarjetaRepository;
import com.apibancario.project_banca.repository.TransaccionRepository;
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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransaccionServiceImplTest {
    @Mock
    private TransaccionRepository transaccionRepository;

    @Mock
    private TarjetaRepository tarjetaRepository;

    @Mock
    private CuentaRepository cuentaRepository;

    @Mock
    private TransaccionMapper transaccionMapper;

    @InjectMocks
    private TransaccionServiceImpl transaccionService;


    private Transaccion transaccion;
    private Transaccion transaccion2;
    private TransactionRequestDto transactionRequestDto;
    private TransactionRequestDto transactionRequestDto2;
    private TransactionResponseDto transactionResponseDto;
    private TransactionResponseDto transactionResponseDto2;
    private Tarjeta tarjeta;
    private Cuenta cuentaDestino;

    @BeforeEach
    void setUp() {
        Cliente cliente = new Cliente(1,"Elias", "Moran", "75484848", "elias@gmail.com",new ArrayList<>(), new ArrayList<>());
        Cliente cliente2 = new Cliente(2,"David", "Bernal", "42585956", "david@gmail.com",new ArrayList<>(), new ArrayList<>());
        Cuenta cuenta = new Cuenta(1,"56445587889696", "AHORRO", "585898", new BigDecimal("40000.00"), cliente, new ArrayList<>(), new ArrayList<>());

        tarjeta = new Tarjeta(1, "4552366895916954", "DEBITO", "09/30", "5058", "548", cuenta, new ArrayList<>());
        cuentaDestino = new Cuenta(2,"22225587838622", "AHORRO", "020202", new BigDecimal("60000.00"), cliente2, new ArrayList<>(), new ArrayList<>());

        ClientResponseDto clientResponseDto = new ClientResponseDto(1,"Elias", "Moran","elias@gmail.com");
        ClientResponseDto clientResponseDto2 = new ClientResponseDto(2,"David", "Bernal","david@gmail.com");
        AccountResponseDto accountResponseDto = new AccountResponseDto(1,"56445587889696", TipoCuenta.AHORRO, clientResponseDto);

        CardResponseDto cardResponseDto = new CardResponseDto(1, "4552366895916954", TipoTarjeta.DEBITO, "09/30", accountResponseDto);
        AccountResponseDto recipientAccountResponseDto = new AccountResponseDto(2,"22225587838622", TipoCuenta.AHORRO, clientResponseDto2);


        transaccion = new Transaccion(
                1,
                "DEPOSITO",
                new BigDecimal("80.00"),
                LocalDateTime.of(2025,9,9,5,16,42),
                tarjeta,
                null
        );

        transaccion2 = new Transaccion(
                2,
                "TRANSFERENCIA",
                new BigDecimal("150.00"),
                LocalDateTime.of(2025,9,9,5,16,42),
                tarjeta,
                cuentaDestino
        );

        transactionRequestDto = new TransactionRequestDto(
                TipoTransaccion.DEPOSITO,
                new BigDecimal("80.00"),
                "4552366895916954",
                "5058",
                null
        );

        transactionRequestDto2 = new TransactionRequestDto(
                TipoTransaccion.TRANSFERENCIA,
                new BigDecimal("150.00"),
                "4552366895916954",
                "5058",
                "22225587838622"
        );

        transactionResponseDto = new TransactionResponseDto(
                1,
                TipoTransaccion.DEPOSITO,
                new BigDecimal("80.00"),
                "09/09/2025 05:16:42",
                cardResponseDto,
                null
        );

        transactionResponseDto2 = new TransactionResponseDto(
                2,
                TipoTransaccion.TRANSFERENCIA,
                new BigDecimal("150.00"),
                "09/09/2025 10:16:42",
                cardResponseDto,
                recipientAccountResponseDto
        );
    }

    @Test
    void testListAll_Success() {
        when(transaccionRepository.findAll()).thenReturn(List.of(transaccion));
        when(transaccionMapper.toTransactionsResponseDto(anyList())).thenReturn(List.of(transactionResponseDto));

        List<TransactionResponseDto> results = transaccionService.listAll();

        assertNotNull(results);
        assertEquals(new BigDecimal("80.00"), results.getFirst().amount());
        assertEquals(1, results.size());
        verify(transaccionRepository, times(1)).findAll();
    }

    @Test
    void testFindById_BadRequestExceptionZero() {
        BadRequestException ex = assertThrows(BadRequestException.class, () -> transaccionService.findById(0) );
        assertEquals("El id es incorrecto", ex.getMessage());
    }

    @Test
    void testFindById_BadRequestExceptionNull() {
        BadRequestException ex = assertThrows(BadRequestException.class, () -> transaccionService.findById(null) );
        assertEquals("El id es incorrecto", ex.getMessage());
    }

    @Test
    void testFindById_ResourceNotFoundException() {
        Integer id = 99;
        when(transaccionRepository.findById(id)).thenReturn(Optional.empty());
        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () -> transaccionService.findById(id));
        assertEquals("No se encontró la transaccion con id: "+id, ex.getMessage());
    }

    @Test
    void testFindById_Success() {
        Integer id = 1;
        when(transaccionRepository.findById(id)).thenReturn(Optional.of(transaccion));
        when( transaccionMapper.toTransactionResponseDto(transaccion)).thenReturn(transactionResponseDto);

        TransactionResponseDto result = transaccionService.findById(id);

        assertNotNull(result);
        assertEquals(TipoTransaccion.DEPOSITO, result.transactionType());
        assertEquals(new BigDecimal("80.00"), result.amount());
        verify(transaccionRepository, times(1)).findById(id);
    }

    @Test
    void testSave_BadRequestExceptionNumeroTarjetaIncorrecta() {
        when(transaccionMapper.toTransaccion(transactionRequestDto)).thenReturn(transaccion);
        when(tarjetaRepository.findByNumeroTarjeta(transactionRequestDto.cardNumber())).thenReturn(Optional.empty());

        BadRequestException ex = assertThrows(BadRequestException.class, () -> transaccionService.save(transactionRequestDto));

        assertEquals("Número de tarjeta incorrecto", ex.getMessage());
    }

    @Test
    void testSave_BadRequestExceptionPinIncorrecto() {
        when(transaccionMapper.toTransaccion(transactionRequestDto)).thenReturn(transaccion);
        when(tarjetaRepository.findByNumeroTarjeta(transactionRequestDto.cardNumber())).thenReturn(Optional.of(tarjeta));

        try (MockedStatic<PasswordUtil> utilMock = Mockito.mockStatic(PasswordUtil.class)) {
            utilMock.when(() -> PasswordUtil.matches(transactionRequestDto.cardPin(), tarjeta.getPinTarjeta()) )
                    .thenReturn(false);


            BadRequestException ex = assertThrows(BadRequestException.class, () -> transaccionService.save(transactionRequestDto));
            assertEquals("PIN de tarjeta incorrecto", ex.getMessage());
        }
    }


}
