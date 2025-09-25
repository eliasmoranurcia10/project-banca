package com.apibancario.model.dto.prestamo;

import com.apibancario.project_banca.model.dto.cliente.ClientResponseDto;
import com.apibancario.project_banca.model.enums.EstadoPrestamo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class LoanResponseDtoTest {

    private LoanResponseDto loanResponseDto;
    private ClientResponseDto clientResponseDto;

    @BeforeEach
    void setUp() {
        clientResponseDto = new ClientResponseDto(
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
    }

    @Test
    void testLoanResponseDtoGetter() {
        assertNotNull(loanResponseDto);
        assertEquals(1, loanResponseDto.loanId());
        assertEquals(new BigDecimal("4000.00"), loanResponseDto.totalAmount());
        assertEquals(new BigDecimal("0.30"), loanResponseDto.interestRate());
        assertEquals(14, loanResponseDto.monthsOfDeadline());
        assertEquals(new BigDecimal("400.00"), loanResponseDto.monthlyFee());
        assertEquals(EstadoPrestamo.APROBADO, loanResponseDto.status());
        assertEquals(clientResponseDto, loanResponseDto.clientResponseDto());
    }

}
