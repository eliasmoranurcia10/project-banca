package com.apibancario.model.dto.cuenta;

import com.apibancario.project_banca.model.enums.TipoCuenta;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record AccountRequestDto(
        TipoCuenta accountType,

        @NotBlank(message = "La clave no tiene que ser vacío")
        @Pattern(regexp = "\\d{6}", message = "La clave tiene 6 dígitos")
        String password,

        @NotNull(message = "El salgo no debe ser nulo")
        @DecimalMin(value = "10.00", message = "El monto debe ser mayor a 10.00")
        BigDecimal saldo,

        @NotNull(message = "El cliente no debe ser nulo")
        Integer clientId
) {
}
