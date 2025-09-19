package com.apibancario.project_banca.model.dto.pagoprestamo;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record LoanPayRequestDto(
        @NotNull(message = "El monto es requerido")
        @Positive(message = "El Monto en un numero positivo")
        BigDecimal paymentAmount,

        @NotNull(message = "Colocar obligatoriamente el id del préstamo")
        @Positive(message = "El id del préstamo es un número positivo")
        Integer loanId
) {
}
