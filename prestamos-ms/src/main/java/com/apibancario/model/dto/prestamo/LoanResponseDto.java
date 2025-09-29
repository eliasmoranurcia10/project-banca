package com.apibancario.model.dto.prestamo;

import com.apibancario.model.enums.EstadoPrestamo;

import java.math.BigDecimal;

public record LoanResponseDto(
        Integer loanId,
        BigDecimal totalAmount,
        BigDecimal interestRate,
        Integer monthsOfDeadline,
        BigDecimal monthlyFee,
        EstadoPrestamo status,
        Integer clientId
) {
}
