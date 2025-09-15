package com.apibancario.project_banca.model.dto.cliente;

public record ClientRequestDto(
        String name,
        String lastName,
        String dni,
        String email
) {
}
