package com.apibancario.project_banca.model.dto.transaccion;

import com.apibancario.project_banca.model.enums.TipoTransaccion;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionRequestDto(
        TipoTransaccion transactionType,
        BigDecimal amount,
        String cardNumber,
        String AccountNumber
) {
}
