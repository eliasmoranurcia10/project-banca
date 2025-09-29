package com.apibancario.model.dto.cuenta;

import com.apibancario.model.dto.cliente.ClientResponseDto;
import com.apibancario.model.enums.TipoCuenta;

public record AccountResponseDto(
        Integer accountId,
        String accountNumber,
        TipoCuenta accountType,
        ClientResponseDto clientResponseDto
) {
}
