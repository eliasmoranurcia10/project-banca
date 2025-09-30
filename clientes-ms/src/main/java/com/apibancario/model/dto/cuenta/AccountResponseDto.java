package com.apibancario.model.dto.cuenta;

import com.apibancario.model.dto.cliente.ClientResponseDto;
import com.apibancario.model.enums.TipoCuenta;

import java.math.BigDecimal;

public record AccountResponseDto(
        Integer accountId,
        String accountNumber,
        TipoCuenta accountType,
        BigDecimal saldo,
        ClientResponseDto clientResponseDto
) {
}
