package com.apibancario.project_banca.model.dto.tarjeta;

import com.apibancario.project_banca.model.dto.cuenta.AccountResponseDto;
import com.apibancario.project_banca.model.enums.TipoTarjeta;

public record CardResponseDto(
        Integer cardId,
        String cardNumber,
        TipoTarjeta cardType,
        String expirationDate,
        AccountResponseDto accountResponseDto
) {
}
