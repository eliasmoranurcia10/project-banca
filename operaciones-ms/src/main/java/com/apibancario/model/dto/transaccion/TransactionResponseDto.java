package com.apibancario.model.dto.transaccion;

import com.apibancario.model.dto.tarjeta.CardResponseDto;
import com.apibancario.model.enums.TipoTransaccion;

import java.math.BigDecimal;

public record TransactionResponseDto(
    Integer transactionId,
    TipoTransaccion transactionType,
    BigDecimal amount,
    String date,
    CardResponseDto cardResponseDto,
    Integer RecipientAccountId
) {
}
