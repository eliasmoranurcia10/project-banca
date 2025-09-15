package com.apibancario.project_banca.model.dto.cliente;

public record ClientResponseDto(
        Integer clientId,
        String name,
        String lastName,
        String email
) {
}
