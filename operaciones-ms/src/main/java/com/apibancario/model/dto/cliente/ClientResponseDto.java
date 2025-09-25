package com.apibancario.model.dto.cliente;

public record ClientResponseDto(
        Integer clientId,
        String name,
        String lastName,
        String email
) {
}
