package com.apibancario.project_banca.model.dto.prestamo;

import com.apibancario.project_banca.model.dto.cliente.ClientResponseDto;

import java.math.BigDecimal;

public record LoanResponseDto(
        Integer loanId,
        BigDecimal totalAmount,
        BigDecimal interestRate,
        Integer monthsOfDeadline,
        ClientResponseDto clientResponseDto
) {
}
