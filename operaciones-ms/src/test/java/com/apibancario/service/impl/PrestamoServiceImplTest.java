package com.apibancario.service.impl;

import com.apibancario.project_banca.exception.BadRequestException;
import com.apibancario.project_banca.exception.InternalServerErrorException;
import com.apibancario.project_banca.exception.ResourceNotFoundException;
import com.apibancario.project_banca.mapper.CuentaMapper;
import com.apibancario.project_banca.mapper.PrestamoMapper;
import com.apibancario.project_banca.model.dto.cliente.ClientResponseDto;
import com.apibancario.project_banca.model.dto.cuenta.AccountRequestDto;
import com.apibancario.project_banca.model.dto.cuenta.AccountResponseDto;
import com.apibancario.project_banca.model.dto.cuenta.PasswordRequestDto;
import com.apibancario.project_banca.model.dto.prestamo.LoanRequestDto;
import com.apibancario.project_banca.model.dto.prestamo.LoanResponseDto;
import com.apibancario.project_banca.model.dto.prestamo.StatusLoanRequestDto;
import com.apibancario.project_banca.model.entity.Cliente;
import com.apibancario.project_banca.model.entity.Cuenta;
import com.apibancario.project_banca.model.entity.Prestamo;
import com.apibancario.project_banca.model.enums.EstadoPrestamo;
import com.apibancario.project_banca.model.enums.TipoCuenta;
import com.apibancario.project_banca.repository.ClienteRepository;
import com.apibancario.project_banca.repository.CuentaRepository;
import com.apibancario.project_banca.repository.PrestamoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.stubbing.answers.DoesNothing;
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
    private ClienteRepository clienteRepository;

    @Mock
    private PrestamoMapper prestamoMapper;

    @InjectMocks
    private PrestamoServiceImpl prestamoService;

    private Prestamo prestamo;
    private LoanRequestDto loanRequestDto;
    private LoanResponseDto loanResponseDto;
    private StatusLoanRequestDto statusLoanRequestDto;
    private Cliente cliente;

    @BeforeEach
    void setUp() {
        cliente = new Cliente(1,"Elias", "Moran", "75484848", "elias@gmail.com",new ArrayList<>(), new ArrayList<>());
        ClientResponseDto clientResponseDto = new ClientResponseDto(1,"Elias", "Moran","elias@gmail.com");

        prestamo = new Prestamo(1,new BigDecimal("4000"), new BigDecimal("0.20"),14, new BigDecimal("400"), "APROBADO", cliente, new ArrayList<>());
        loanRequestDto = new LoanRequestDto(new BigDecimal("4000"), new BigDecimal("0.20"),14, EstadoPrestamo.APROBADO, "75484848");
        loanResponseDto = new LoanResponseDto(1,new BigDecimal("4000"), new BigDecimal("0.20"),14, new BigDecimal("400"), EstadoPrestamo.APROBADO, clientResponseDto );
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
        Integer id= null;
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
    void testSave_ResourceNotFoundException() {
        when(prestamoMapper.toPrestamo(loanRequestDto)).thenReturn(prestamo);
        when(clienteRepository.findByDni(loanRequestDto.dni())).thenReturn(Optional.empty());

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () -> prestamoService.save(loanRequestDto));
        assertEquals("No se encontró el dni del cliente", ex.getMessage());
    }

    @Test
    void testSave_BadRequestException() {
        when(prestamoMapper.toPrestamo(loanRequestDto)).thenReturn(prestamo);
        when(clienteRepository.findByDni(loanRequestDto.dni())).thenReturn(Optional.of(cliente));
        when(prestamoRepository.save(prestamo)).thenThrow(new RuntimeException("DB error"));

        BadRequestException ex = assertThrows(BadRequestException.class, () -> prestamoService.save(loanRequestDto));
        assertEquals("Error al crear nuevo préstamo, ingresar datos correctos", ex.getMessage());
    }

    @Test
    void testSave_Success() {
        when(prestamoMapper.toPrestamo(loanRequestDto)).thenReturn(prestamo);
        when(clienteRepository.findByDni(loanRequestDto.dni())).thenReturn(Optional.of(cliente));
        when(prestamoRepository.save(prestamo)).thenReturn(prestamo);
        when(prestamoMapper.toLoanResponseDto(prestamo)).thenReturn(loanResponseDto);

        LoanResponseDto result = prestamoService.save(loanRequestDto);
        assertNotNull(result);
        assertEquals(new BigDecimal("4000"), result.totalAmount());
        verify(clienteRepository, times(1)).findByDni(loanRequestDto.dni());
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
