package com.apibancario.model.dto.transaccion;

import com.apibancario.model.enums.TipoTransaccion;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record TransactionRequestDto(
        @NotNull(message = "El tipo de transacción es requerido")
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

        @JsonInclude(JsonInclude.Include.NON_NULL)
        @Positive(message = "El id de la cuenta destino debe ser positivo")
        Integer recipientAccountId
) {
}
