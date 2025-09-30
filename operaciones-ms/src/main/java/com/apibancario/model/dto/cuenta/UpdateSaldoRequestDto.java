package com.apibancario.model.dto.cuenta;

import com.apibancario.model.enums.TipoOperacion;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record UpdateSaldoRequestDto(

        @NotNull(message = "El tipo de operación es obligatorio")
        TipoOperacion tipoOperacion,

        @NotNull(message = "El monto no debe ser nulo")
        @Positive(message = "El monto debe ser positivo")
        BigDecimal amount
) {
}
