package com.apibancario.service;

import com.apibancario.model.dto.cuenta.AccountResponseDto;

public interface AccountCardService {
    AccountResponseDto getAccountById(Integer id);
    AccountResponseDto getAccountByNumberAccount(String numeroCuenta);
}
