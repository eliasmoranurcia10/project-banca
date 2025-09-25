package com.apibancario.model.dto.prestamo;

import com.apibancario.project_banca.model.dto.cliente.ClientResponseDto;
import com.apibancario.project_banca.model.enums.EstadoPrestamo;

import java.math.BigDecimal;

public record LoanResponseDto(
        Integer loanId,
        BigDecimal totalAmount,
        BigDecimal interestRate,
        Integer monthsOfDeadline,
        BigDecimal monthlyFee,
        EstadoPrestamo status,
        ClientResponseDto clientResponseDto
) {
}
