package com.apibancario.model.dto.tarjeta;

import com.apibancario.model.enums.TipoTarjeta;

public record CardResponseDto(
        Integer cardId,
        String cardNumber,
        TipoTarjeta cardType,
        String expirationDate,
        Integer accountId
) {
}
