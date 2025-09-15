package com.apibancario.project_banca.model.dto.cuenta;

import com.apibancario.project_banca.model.enums.TipoCuenta;

import java.math.BigDecimal;

public record AccountRequestDto(
        TipoCuenta accountType,
        String password,
        BigDecimal saldo,
        Integer clientId
) {
}
