package com.apibancario.project_banca.service.impl;

import com.apibancario.project_banca.model.dto.transaccion.TransactionRequestDto;
import com.apibancario.project_banca.model.dto.transaccion.TransactionResponseDto;
import com.apibancario.project_banca.service.TransaccionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransaccionServiceImpl implements TransaccionService {
    @Override
    public List<TransactionResponseDto> listAll() {
        return List.of();
    }

    @Override
    public TransactionResponseDto findById(Integer id) {
        return null;
    }

    @Override
    public TransactionResponseDto save(TransactionRequestDto transactionRequestDto) {
        return null;
    }

    @Override
    public TransactionResponseDto update(Integer id, TransactionRequestDto transactionRequestDto) {
        return null;
    }

    @Override
    public void delete(Integer id) {

    }
}
