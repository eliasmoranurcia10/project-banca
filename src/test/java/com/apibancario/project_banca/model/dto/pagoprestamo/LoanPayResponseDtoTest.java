package com.apibancario.project_banca.model.dto.pagoprestamo;

import com.apibancario.project_banca.model.dto.cliente.ClientResponseDto;
import com.apibancario.project_banca.model.dto.prestamo.LoanResponseDto;
import com.apibancario.project_banca.model.enums.EstadoPrestamo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class LoanPayResponseDtoTest {

    private LoanPayResponseDto loanPayResponseDto;
    private LoanResponseDto loanResponseDto;

    @BeforeEach
    void setUp() {

        ClientResponseDto clientResponseDto = new ClientResponseDto(
                1,
                "Juan",
                "Pérez",
                "juan.perez@mail.com"
        );

        loanResponseDto = new LoanResponseDto(
                1,
                new BigDecimal("4000.00"),
                new BigDecimal("0.30"),
                14,
                new BigDecimal("400.00"),
                EstadoPrestamo.APROBADO,
                clientResponseDto
        );

        loanPayResponseDto = new LoanPayResponseDto(
                1,
                new BigDecimal("400.00"),
                "10/08/2025 13:46:ss",
                loanResponseDto
        );
    }

    @Test
    void testLoanPayResponseDtoGetter() {
        assertNotNull(loanPayResponseDto);
        assertEquals(1, loanPayResponseDto.payId());
        assertEquals(new BigDecimal("400.00"), loanPayResponseDto.paymentAmount());
        assertEquals("10/08/2025 13:46:ss", loanPayResponseDto.paymentDate());
        assertEquals(loanResponseDto, loanPayResponseDto.loanResponseDto());
    }

}
