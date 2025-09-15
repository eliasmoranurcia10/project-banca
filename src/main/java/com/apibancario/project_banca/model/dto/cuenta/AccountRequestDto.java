package com.apibancario.project_banca.model.dto.cuenta;

import java.math.BigDecimal;

public record AccountRequestDto(
        String accountNumber,
        String accountType,
        String password,
        BigDecimal saldo,
        Integer clientId
) {
}
