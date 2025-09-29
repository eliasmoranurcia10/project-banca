package com.apibancario.model.dto.prestamo;

import com.apibancario.model.enums.EstadoPrestamo;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record LoanRequestDto(
        @NotNull(message = "El monto es requerido")
        @Positive(message = "El Monto en un numero positivo")
        @DecimalMin(value = "500.00", inclusive = true, message = "El monto mínimo es de 500 soles")
        BigDecimal totalAmount,

        @NotNull(message = "La tasa de interés es obligatoria")
        @DecimalMin(value = "0.0001", inclusive = true, message = "La tasa de interés debe ser mayor que 0")
        @DecimalMax(value = "1.0", message = "La tasa de interés no puede ser mayor a 1 (100%)")
        @Digits(integer = 1, fraction = 6, message = "La tasa de interés debe tener máximo 6 decimales")
        BigDecimal interestRate,

        @NotNull(message = "El plazo en meses es obligatorio")
        @Min(value = 6, message = "El plazo mínimo es de 6 meses")
        @Max(value = 72, message = "El plazo máximo es de 72 meses")
        Integer monthsOfDeadline,

        @NotNull(message = "El estado del préstamo es obligatorio")
        EstadoPrestamo status,

        @NotNull(message = "Agregar el id del cliente")
        @Positive(message = "El id del cliente en un numero positivo")
        Integer clientId
) {
}
