package com.apibancario.model.dto.pagoprestamo;

import com.apibancario.model.dto.prestamo.LoanResponseDto;

import java.math.BigDecimal;

public record LoanPayResponseDto(
        Integer payId,
        BigDecimal paymentAmount,
        String paymentDate,
        LoanResponseDto loanResponseDto
) {
}
