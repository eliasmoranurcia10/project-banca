package com.apibancario.service;

import com.apibancario.project_banca.model.dto.cuenta.AccountRequestDto;
import com.apibancario.project_banca.model.dto.cuenta.AccountResponseDto;
import com.apibancario.project_banca.model.dto.cuenta.PasswordRequestDto;

import java.util.List;

public interface CuentaService {
    List<AccountResponseDto> listAll();
    AccountResponseDto findById(Integer id);
    AccountResponseDto save(AccountRequestDto accountRequestDto);
    AccountResponseDto updatePassword(Integer id, PasswordRequestDto passwordRequestDto);
    void delete(Integer id);
}
