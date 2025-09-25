package com.apibancario.service;

import com.apibancario.project_banca.model.dto.transaccion.TransactionRequestDto;
import com.apibancario.project_banca.model.dto.transaccion.TransactionResponseDto;

import java.util.List;

public interface TransaccionService {
    List<TransactionResponseDto> listAll();
    TransactionResponseDto findById(Integer id);
    TransactionResponseDto save(TransactionRequestDto transactionRequestDto);
}
