package com.apibancario.model.dto.tarjeta;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record CardPinRequestDto(
        @NotBlank(message = "La clave no tiene que ser vacío")
        @Pattern(regexp = "\\d{4}", message = "La clave tiene 4 dígitos")
        String oldPin,

        @NotBlank(message = "La clave no tiene que ser vacío")
        @Pattern(regexp = "\\d{4}", message = "La clave tiene 4 dígitos")
        String newPin
) {
}
