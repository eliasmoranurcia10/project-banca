package com.apibancario.service;

import com.apibancario.model.dto.cuenta.AccountRequestDto;
import com.apibancario.model.dto.cuenta.AccountResponseDto;
import com.apibancario.model.dto.cuenta.PasswordRequestDto;
import com.apibancario.model.dto.cuenta.UpdateSaldoRequestDto;
import com.apibancario.model.enums.TipoOperacion;

import java.math.BigDecimal;
import java.util.List;

public interface CuentaService {
    List<AccountResponseDto> listAll();
    AccountResponseDto findById(Integer id);
    AccountResponseDto findByNumberAccount(String numeroCuenta);
    AccountResponseDto save(AccountRequestDto accountRequestDto);
    AccountResponseDto updatePassword(Integer id, PasswordRequestDto passwordRequestDto);
    AccountResponseDto updateSaldo(Integer id, UpdateSaldoRequestDto updateSaldoRequestDto);
    void delete(Integer id);
}
