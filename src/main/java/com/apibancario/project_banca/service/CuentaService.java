package com.apibancario.project_banca.service;

import com.apibancario.project_banca.model.dto.cuenta.AccountRequestDto;
import com.apibancario.project_banca.model.dto.cuenta.AccountResponseDto;

import java.util.List;

public interface CuentaService {
    List<AccountResponseDto> listAll();
    AccountResponseDto findById(Integer id);
    AccountResponseDto save(AccountRequestDto accountRequestDto);
    AccountResponseDto update(Integer id, AccountRequestDto accountRequestDto);
    void delete(Integer id);
}
