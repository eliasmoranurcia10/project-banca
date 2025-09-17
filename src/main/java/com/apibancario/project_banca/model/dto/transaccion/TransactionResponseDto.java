package com.apibancario.project_banca.model.dto.transaccion;

import com.apibancario.project_banca.model.dto.cuenta.AccountResponseDto;
import com.apibancario.project_banca.model.dto.tarjeta.CardResponseDto;
import com.apibancario.project_banca.model.enums.TipoTransaccion;

import java.math.BigDecimal;

public record TransactionResponseDto(
    Integer transactionId,
    TipoTransaccion transactionType,
    BigDecimal amount,
    String date,
    CardResponseDto cardResponseDto,
    AccountResponseDto recipientAccountResponseDto
) {
}
