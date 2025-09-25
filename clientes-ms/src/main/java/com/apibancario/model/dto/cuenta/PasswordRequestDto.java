package com.apibancario.model.dto.cuenta;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record PasswordRequestDto(
        @NotBlank(message = "La clave no tiene que ser vacío")
        @Pattern(regexp = "\\d{6}", message = "La clave tiene 6 dígitos")
        String oldPassword,

        @NotBlank(message = "La clave no tiene que ser vacío")
        @Pattern(regexp = "\\d{6}", message = "La clave tiene 6 dígitos")
        String newPassword
) {
}
