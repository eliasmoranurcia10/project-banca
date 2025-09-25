package com.apibancario.service.impl;

import com.apibancario.project_banca.exception.BadRequestException;
import com.apibancario.project_banca.exception.InternalServerErrorException;
import com.apibancario.project_banca.exception.ResourceNotFoundException;
import com.apibancario.project_banca.mapper.PagoPrestamoMapper;
import com.apibancario.project_banca.mapper.PrestamoMapper;
import com.apibancario.project_banca.model.dto.cliente.ClientResponseDto;
import com.apibancario.project_banca.model.dto.pagoprestamo.LoanPayRequestDto;
import com.apibancario.project_banca.model.dto.pagoprestamo.LoanPayResponseDto;
import com.apibancario.project_banca.model.dto.prestamo.LoanResponseDto;
import com.apibancario.project_banca.model.entity.Cliente;
import com.apibancario.project_banca.model.entity.PagoPrestamo;
import com.apibancario.project_banca.model.entity.Prestamo;
import com.apibancario.project_banca.model.enums.EstadoPrestamo;
import com.apibancario.project_banca.repository.ClienteRepository;
import com.apibancario.project_banca.repository.PagoPrestamoRepository;
import com.apibancario.project_banca.repository.PrestamoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PagoPrestamoServiceImplTest {

    @Mock
    private PagoPrestamoRepository pagoPrestamoRepository;

    @Mock
    private PrestamoRepository prestamoRepository;

    @Mock
    private PagoPrestamoMapper pagoPrestamoMapper;

    @InjectMocks
    private PagoPrestamoServiceImpl pagoPrestamoService;

    private PagoPrestamo pagoPrestamo;
    private LoanPayRequestDto loanPayRequestDto;
    private LoanPayResponseDto loanPayResponseDto;

    private Prestamo prestamo;
    private LoanResponseDto loanResponseDto;

    @BeforeEach
    void setUp() {
        Cliente cliente = new Cliente(1,"Elias", "Moran", "75484848", "elias@gmail.com",new ArrayList<>(), new ArrayList<>());
        ClientResponseDto clientResponseDto = new ClientResponseDto(1,"Elias", "Moran","elias@gmail.com");

        prestamo = new Prestamo(1,new BigDecimal("4000"), new BigDecimal("0.20"),14, new BigDecimal("400"), "APROBADO", cliente, new ArrayList<>());
        loanResponseDto = new LoanResponseDto(1,new BigDecimal("4000"), new BigDecimal("0.20"),14, new BigDecimal("400"), EstadoPrestamo.APROBADO, clientResponseDto );

        pagoPrestamo = new PagoPrestamo(1,new BigDecimal("400"), LocalDateTime.of(2025, 9,19,11,42,5), prestamo);
        loanPayRequestDto = new LoanPayRequestDto(new BigDecimal("400"), 1);
        loanPayResponseDto = new LoanPayResponseDto(1, new BigDecimal("400"), "19/09/2025 11:42:05", loanResponseDto);
    }

    @Test
    void testListAll_Success(){
        when(pagoPrestamoRepository.findAll()).thenReturn(List.of(pagoPrestamo));
        when(pagoPrestamoMapper.toLoansPayResponseDto(List.of(pagoPrestamo))).thenReturn(List.of(loanPayResponseDto));

        List<LoanPayResponseDto> result = pagoPrestamoService.listAll();

        assertNotNull(result);
        assertEquals("19/09/2025 11:42:05", result.getFirst().paymentDate());
        assertEquals(1, result.size());
        verify(pagoPrestamoRepository, times(1)).findAll();
    }

    @Test
    void testFindById_BadRequestExceptionZero(){
        Integer id = 0;
        BadRequestException ex = assertThrows(BadRequestException.class, () -> pagoPrestamoService.findById(id));
        assertEquals("El id es incorrecto", ex.getMessage());
    }

    @Test
    void testFindById_BadRequestExceptionNull(){
        Integer id = null;
        BadRequestException ex = assertThrows(BadRequestException.class, () -> pagoPrestamoService.findById(id));
        assertEquals("El id es incorrecto", ex.getMessage());
    }

    @Test
    void testFindById_ResourceNotFoundException() {
        Integer id = 99;
        when(pagoPrestamoRepository.findById(id)).thenReturn(Optional.empty());
        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () -> pagoPrestamoService.findById(id));
        assertEquals("No se encontró el prestamo con id: "+id, ex.getMessage());
    }

    @Test
    void testFindById_Success() {
        Integer id = 1;
        when(pagoPrestamoRepository.findById(id)).thenReturn(Optional.of(pagoPrestamo));
        when(pagoPrestamoMapper.toLoanPayResponseDto(pagoPrestamo)).thenReturn(loanPayResponseDto);
        LoanPayResponseDto result = pagoPrestamoService.findById(id);
        assertNotNull(result);
        assertEquals("19/09/2025 11:42:05", result.paymentDate());
        verify(pagoPrestamoRepository).findById(id);
    }

    @Test
    void testSave_ResourceNotFoundExceptionPrestamoNoEncontrado() {
        when(prestamoRepository.findById(loanPayRequestDto.loanId())).thenReturn(Optional.empty());
        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, ()-> pagoPrestamoService.save(loanPayRequestDto) );
        assertEquals("Préstamo no encontrado", ex.getMessage());
    }

    @Test
    void testSave_BadRequestExceptionSolicitado() {
        Cliente cliente = new Cliente(1,"Elias", "Moran", "75484848", "elias@gmail.com",new ArrayList<>(), new ArrayList<>());
        Prestamo prestamoSolicitado = new Prestamo(1,new BigDecimal("4000"), new BigDecimal("0.20"),14, new BigDecimal("400"), "SOLICITADO", cliente, new ArrayList<>());
        when(prestamoRepository.findById(loanPayRequestDto.loanId())).thenReturn(Optional.of(prestamoSolicitado));
        BadRequestException ex = assertThrows(BadRequestException.class, () -> pagoPrestamoService.save(loanPayRequestDto) );
        assertEquals("No puede realizar pagos al préstamo por encontrarse: "+ prestamoSolicitado.getEstado(), ex.getMessage());
    }

    @Test
    void testSave_BadRequestExceptionLiquidado() {
        Cliente cliente = new Cliente(1,"Elias", "Moran", "75484848", "elias@gmail.com",new ArrayList<>(), new ArrayList<>());
        Prestamo prestamoLiquidado = new Prestamo(1,new BigDecimal("4000"), new BigDecimal("0.20"),14, new BigDecimal("400"), "LIQUIDADO", cliente, new ArrayList<>());
        when(prestamoRepository.findById(loanPayRequestDto.loanId())).thenReturn(Optional.of(prestamoLiquidado));
        BadRequestException ex = assertThrows(BadRequestException.class, () -> pagoPrestamoService.save(loanPayRequestDto) );
        assertEquals("No puede realizar pagos al préstamo por encontrarse: "+ prestamoLiquidado.getEstado(), ex.getMessage());
    }

    @Test
    void testSave_BadRequestExceptionCuotaIncorrecta() {
        LoanPayRequestDto loanPayRequestDtoIncorrect = new LoanPayRequestDto(new BigDecimal("200"), 1);
        when(prestamoRepository.findById(loanPayRequestDtoIncorrect.loanId())).thenReturn(Optional.of(prestamo));
        BadRequestException ex = assertThrows(BadRequestException.class, () -> pagoPrestamoService.save(loanPayRequestDtoIncorrect));
        assertEquals("Cuota incorrecta. Su cuota es de:" + prestamo.getCuotaMensual() , ex.getMessage());
    }

    @Test
    void testSave_InternalServerErrorException() {
        when(prestamoRepository.findById(loanPayRequestDto.loanId())).thenReturn(Optional.of(prestamo));
        when(pagoPrestamoMapper.toPagoPrestamo(loanPayRequestDto)).thenReturn(pagoPrestamo);
        pagoPrestamo.setPrestamo(prestamo);
        when(pagoPrestamoRepository.save( pagoPrestamo )).thenThrow(new RuntimeException("DB error"));
        InternalServerErrorException ex = assertThrows(InternalServerErrorException.class, () -> pagoPrestamoService.save(loanPayRequestDto));
        assertEquals("Error al guardar pago de préstamo", ex.getMessage());
    }

    @Test
    void testSave_Success() {
        when(prestamoRepository.findById(loanPayRequestDto.loanId())).thenReturn(Optional.of(prestamo));
        when(pagoPrestamoMapper.toPagoPrestamo(loanPayRequestDto)).thenReturn(pagoPrestamo);
        pagoPrestamo.setPrestamo(prestamo);
        when(pagoPrestamoRepository.save( pagoPrestamo )).thenReturn(pagoPrestamo);
        when(pagoPrestamoMapper.toLoanPayResponseDto(pagoPrestamo)).thenReturn(loanPayResponseDto);
        LoanPayResponseDto result = pagoPrestamoService.save(loanPayRequestDto);
        assertNotNull(result);
        assertEquals("19/09/2025 11:42:05", result.paymentDate());
        verify(pagoPrestamoRepository, times(1)).save(pagoPrestamo);
    }

}
