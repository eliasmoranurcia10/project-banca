package com.apibancario.model.dto.cuenta;

import com.apibancario.model.enums.TipoOperacion;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record UpdateSaldoRequestDto(

        @NotNull(message = "El tipo de operación es obligatorio")
        TipoOperacion tipoOperacion,

        @NotNull(message = "El monto no debe ser nulo")
        @Positive(message = "El monto debe ser positivo")
        BigDecimal amount
) {
}
