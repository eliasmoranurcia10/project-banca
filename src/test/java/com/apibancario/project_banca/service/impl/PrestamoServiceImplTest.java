package com.apibancario.project_banca.service.impl;

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
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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



}
