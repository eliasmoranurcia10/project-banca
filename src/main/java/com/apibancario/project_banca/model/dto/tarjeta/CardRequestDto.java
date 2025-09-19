package com.apibancario.project_banca.model.dto.tarjeta;

import com.apibancario.project_banca.model.enums.TipoTarjeta;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record CardRequestDto (
        TipoTarjeta cardType,
        @NotBlank(message = "La clave no tiene que ser vacío")
        @Pattern(regexp = "\\d{4}", message = "La clave tiene 4 dígitos")
        String cardPin,
        @NotNull(message = "La cuenta no debe ser nula")
        @Positive(message = "El id de cuenta debe ser un número positivo")
        Integer accountId
) {
}
