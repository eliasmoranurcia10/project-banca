package com.apibancario.project_banca.model.dto.pagoprestamo;

import java.math.BigDecimal;

public record LoanPayRequestDto(
        BigDecimal paymentAmount,
        Integer loanId
) {
}
