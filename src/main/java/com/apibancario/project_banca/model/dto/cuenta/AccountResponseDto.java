package com.apibancario.project_banca.model.dto.cuenta;

import com.apibancario.project_banca.model.dto.cliente.ClientResponseDto;
import com.apibancario.project_banca.model.enums.TipoCuenta;

public record AccountResponseDto(
        Integer accountId,
        String accountNumber,
        TipoCuenta accountType,
        ClientResponseDto clientResponseDto
) {
}
