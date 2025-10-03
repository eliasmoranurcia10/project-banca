package com.apibancario.service.impl;

import com.apibancario.exception.BadRequestException;
import com.apibancario.exception.InternalServerErrorException;
import com.apibancario.exception.ResourceNotFoundException;
import com.apibancario.mapper.PrestamoMapper;
import com.apibancario.model.dto.prestamo.LoanRequestDto;
import com.apibancario.model.dto.prestamo.LoanResponseDto;
import com.apibancario.model.dto.prestamo.StatusLoanRequestDto;
import com.apibancario.model.entity.Prestamo;
import com.apibancario.model.enums.EstadoPrestamo;
import com.apibancario.repository.PrestamoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PrestamoServiceImplTest {

    @Mock
    private PrestamoRepository prestamoRepository;

    @Mock
    private PrestamoMapper prestamoMapper;

    @InjectMocks
    private PrestamoServiceImpl prestamoService;

    private Prestamo prestamo;
    private LoanRequestDto loanRequestDto;
    private LoanResponseDto loanResponseDto;
    private StatusLoanRequestDto statusLoanRequestDto;

    @BeforeEach
    void setUp() {

        prestamo = new Prestamo(1,new BigDecimal("4000"), new BigDecimal("0.20"),14, new BigDecimal("400"), "APROBADO", 1, new ArrayList<>());
        loanRequestDto = new LoanRequestDto(new BigDecimal("4000"), new BigDecimal("0.20"),14, EstadoPrestamo.APROBADO, 1);
        loanResponseDto = new LoanResponseDto(1,new BigDecimal("4000"), new BigDecimal("0.20"),14, new BigDecimal("400"), EstadoPrestamo.APROBADO, 1 );
        statusLoanRequestDto = new StatusLoanRequestDto(EstadoPrestamo.LIQUIDADO);
    }

    @Test
    void testListAll_Success(){
        when(prestamoRepository.findAll()).thenReturn(List.of(prestamo));
        when(prestamoMapper.toLoansResponseDto(List.of(prestamo))).thenReturn(List.of(loanResponseDto));

        List<LoanResponseDto> result = prestamoService.listAll();

        assertNotNull(result);
        assertEquals(new BigDecimal("400"), result.getFirst().monthlyFee());
        assertEquals(1, result.size());
        verify(prestamoRepository, times(1)).findAll();
    }

    @Test
    void testFindById_BadRequestExceptionZero() {
        Integer id= 0;
        BadRequestException ex = assertThrows(BadRequestException.class, () -> prestamoService.findById(id) );
        assertEquals("El id es incorrecto", ex.getMessage());
    }

    @Test
    void testFindById_BadRequestExceptionNull() {
        BadRequestException ex = assertThrows(BadRequestException.class, () -> prestamoService.findById(null) );
        assertEquals("El id es incorrecto", ex.getMessage());
    }

    @Test
    void testFindById_ResourceNotFoundException() {
        Integer id = 99;
        when(prestamoRepository.findById(id)).thenReturn(Optional.empty());
        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () -> prestamoService.findById(id) );
        assertEquals("No se encuentra un préstamo con el id: "+id, ex.getMessage());
    }

    @Test
    void testFindById_Success() {
        Integer id = 1;
        when(prestamoRepository.findById(id)).thenReturn(Optional.of(prestamo));
        when(prestamoMapper.toLoanResponseDto(prestamo)).thenReturn(loanResponseDto);
        LoanResponseDto result = prestamoService.findById(id);
        assertNotNull(result);
        assertEquals(1, result.loanId());
        assertEquals(new BigDecimal("4000"), result.totalAmount());
        assertEquals(14, result.monthsOfDeadline());
        verify(prestamoRepository, times(1)).findById(id);
    }

    @Test
    void testUpdateStatusLoan_BadRequestExceptionZero() {
        BadRequestException ex = assertThrows(BadRequestException.class, () -> prestamoService.updateStatusLoan(0,statusLoanRequestDto));
        assertEquals("El id es incorrecto", ex.getMessage());
    }

    @Test
    void testUpdateStatusLoan_BadRequestExceptionNull() {
        BadRequestException ex = assertThrows(BadRequestException.class, () -> prestamoService.updateStatusLoan(null,statusLoanRequestDto));
        assertEquals("El id es incorrecto", ex.getMessage());
    }

    @Test
    void testUpdateStatusLoan_ResourceNotFoundException() {
        Integer id = 99;
        when(prestamoRepository.findById(id)).thenReturn(Optional.empty());

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () -> prestamoService.updateStatusLoan(id, statusLoanRequestDto));
        assertEquals("No se encontró el préstamo con el id: "+id, ex.getMessage());
    }

    @Test
    void testUpdateStatusLoan_InternalServerErrorException() {
        Integer id = 1;
        when(prestamoRepository.findById(id)).thenReturn(Optional.of(prestamo));
        doNothing().when(prestamoMapper).updateEstadoPrestamoFromDto(statusLoanRequestDto, prestamo);
        when(prestamoRepository.save(prestamo)).thenThrow(new RuntimeException("DB error"));
        InternalServerErrorException ex = assertThrows(InternalServerErrorException.class, () -> prestamoService.updateStatusLoan(id, statusLoanRequestDto));
        assertEquals("Error al actualizar el estado del préstamo", ex.getMessage());
    }

    @Test
    void testUpdateStatusLoan_Success() {
        Integer id = 1;
        when(prestamoRepository.findById(id)).thenReturn(Optional.of(prestamo));
        doNothing().when(prestamoMapper).updateEstadoPrestamoFromDto(statusLoanRequestDto, prestamo);
        when(prestamoRepository.save(prestamo)).thenReturn(prestamo);
        when(prestamoMapper.toLoanResponseDto(prestamo)).thenReturn(loanResponseDto);

        LoanResponseDto result = prestamoService.updateStatusLoan(id, statusLoanRequestDto);
        assertNotNull(result);
        assertEquals(new BigDecimal("4000"), result.totalAmount());
        verify(prestamoRepository,times(1)).save(prestamo);
    }
}
