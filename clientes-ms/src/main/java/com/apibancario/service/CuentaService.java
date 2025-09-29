package com.apibancario.service;

import com.apibancario.model.dto.cuenta.AccountRequestDto;
import com.apibancario.model.dto.cuenta.AccountResponseDto;
import com.apibancario.model.dto.cuenta.PasswordRequestDto;

import java.util.List;

public interface CuentaService {
    List<AccountResponseDto> listAll();
    AccountResponseDto findById(Integer id);
    AccountResponseDto findByNumberAccount(String numeroCuenta);
    AccountResponseDto save(AccountRequestDto accountRequestDto);
    AccountResponseDto updatePassword(Integer id, PasswordRequestDto passwordRequestDto);
    void delete(Integer id);
}
