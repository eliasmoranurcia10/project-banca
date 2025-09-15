package com.apibancario.project_banca.model.dto.prestamo;

import java.math.BigDecimal;

public record LoanRequestDto(
        BigDecimal totalAmount,
        BigDecimal interestRate,
        Integer monthsOfDeadline,
        String dni
) {
}
