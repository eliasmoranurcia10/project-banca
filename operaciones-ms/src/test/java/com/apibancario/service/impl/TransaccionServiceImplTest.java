package com.apibancario.service.impl;

import com.apibancario.exception.BadRequestException;
import com.apibancario.exception.ResourceNotFoundException;
import com.apibancario.mapper.TransaccionMapper;
import com.apibancario.model.dto.tarjeta.CardResponseDto;
import com.apibancario.model.dto.transaccion.TransactionRequestDto;
import com.apibancario.model.dto.transaccion.TransactionResponseDto;
import com.apibancario.model.entity.Tarjeta;
import com.apibancario.model.entity.Transaccion;
import com.apibancario.model.enums.TipoTarjeta;
import com.apibancario.model.enums.TipoTransaccion;
import com.apibancario.repository.TarjetaRepository;
import com.apibancario.repository.TransaccionRepository;
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
    private TransaccionMapper transaccionMapper;

    @InjectMocks
    private TransaccionServiceImpl transaccionService;


    private Transaccion transaccion;
    private Transaccion transaccion2;
    private Transaccion transaccion3;
    private Transaccion transaccion4;
    private TransactionRequestDto transactionRequestDto;
    private TransactionRequestDto transactionRequestDto2;
    private TransactionRequestDto transactionRequestDto3;
    private TransactionRequestDto transactionRequestDto4;
    private TransactionResponseDto transactionResponseDto;
    private TransactionResponseDto transactionResponseDto2;
    private TransactionResponseDto transactionResponseDto3;
    private TransactionResponseDto transactionResponseDto4;
    private Tarjeta tarjeta;

    @BeforeEach
    void setUp() {

        tarjeta = new Tarjeta(1, "4552366895916954", "DEBITO", "09/30", "5058", "548", 1, new ArrayList<>());
        CardResponseDto cardResponseDto = new CardResponseDto(1, "4552366895916954", TipoTarjeta.DEBITO, "09/30", 1);

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
                1
        );

        transaccion3 = new Transaccion(
                1,
                "RETIRO",
                new BigDecimal("80.00"),
                LocalDateTime.of(2025,9,9,5,16,42),
                tarjeta,
                null
        );

        transaccion4 = new Transaccion(
                2,
                "PAGO",
                new BigDecimal("150.00"),
                LocalDateTime.of(2025,9,9,5,16,42),
                tarjeta,
                1
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
                1
        );

        transactionRequestDto3 = new TransactionRequestDto(
                TipoTransaccion.RETIRO,
                new BigDecimal("80.00"),
                "4552366895916954",
                "5058",
                null
        );

        transactionRequestDto4 = new TransactionRequestDto(
                TipoTransaccion.PAGO,
                new BigDecimal("150.00"),
                "4552366895916954",
                "5058",
                1
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
                1
        );

        transactionResponseDto3 = new TransactionResponseDto(
                1,
                TipoTransaccion.RETIRO,
                new BigDecimal("80.00"),
                "09/09/2025 05:16:42",
                cardResponseDto,
                null
        );

        transactionResponseDto4 = new TransactionResponseDto(
                2,
                TipoTransaccion.PAGO,
                new BigDecimal("150.00"),
                "09/09/2025 10:16:42",
                cardResponseDto,
                1
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
    void testListAll_SuccessWithRecipient() {
        when(transaccionRepository.findAll()).thenReturn(List.of(transaccion2));
        when(transaccionMapper.toTransactionsResponseDto(anyList())).thenReturn(List.of(transactionResponseDto2));

        List<TransactionResponseDto> results = transaccionService.listAll();

        assertNotNull(results);
        assertEquals(new BigDecimal("150.00"), results.getFirst().amount());
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
