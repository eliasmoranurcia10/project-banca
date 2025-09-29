package com.apibancario.model.dto.prestamo;

import com.apibancario.model.enums.EstadoPrestamo;
import jakarta.validation.constraints.NotNull;

public record StatusLoanRequestDto(
        @NotNull(message = "El estado del préstamo es obligatorio")
        EstadoPrestamo status
) {
}
