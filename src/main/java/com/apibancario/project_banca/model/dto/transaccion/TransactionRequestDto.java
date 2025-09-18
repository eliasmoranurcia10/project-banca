package com.apibancario.project_banca.model.dto.transaccion;

import com.apibancario.project_banca.model.enums.TipoTransaccion;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.Optional;

public record TransactionRequestDto(
        TipoTransaccion transactionType,
        @NotNull(message = "El monto es requerido")
        @Positive(message = "El Monto en un numero positivo")
        BigDecimal amount,

        @NotBlank(message = "El número de tarjeta no debe estar vacío")
        @Pattern(regexp = "\\d{16}", message = "La clave tiene 16 dígitos")
        String cardNumber,

        @NotBlank(message = "La clave no tiene que ser vacío")
        @Pattern(regexp = "\\d{4}", message = "La clave tiene 4 dígitos")
        String cardPin,

        String RecipientAccountNumber
) {
}
