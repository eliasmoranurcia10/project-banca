package com.apibancario.model.dto.cliente;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record ClientRequestDto(
        @NotBlank(message = "El nombre es obligatorio")
        String name,

        @NotBlank(message = "El apellido es obligatorio")
        String lastName,

        @NotBlank(message = "El dni es obligatorio")
        @Pattern(regexp = "\\d{8}", message = "El DNI debe ser exactamente 8 dígitos")
        String dni,

        @NotBlank(message = "El email es obligatorio")
        @Email(message = "El correo debe ser válido")
        String email
) {
}
