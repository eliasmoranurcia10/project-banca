package com.apibancario.project_banca.service.impl;

import com.apibancario.project_banca.model.dto.cuenta.AccountRequestDto;
import com.apibancario.project_banca.model.dto.cuenta.AccountResponseDto;
import com.apibancario.project_banca.service.CuentaService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CuentaServiceImpl implements CuentaService {
    @Override
    public List<AccountResponseDto> listAll() {
        return List.of();
    }

    @Override
    public AccountResponseDto findById(Integer id) {
        return null;
    }

    @Override
    public AccountResponseDto save(AccountRequestDto accountRequestDto) {
        return null;
    }

    @Override
    public AccountResponseDto update(Integer id, AccountRequestDto accountRequestDto) {
        return null;
    }

    @Override
    public void delete(Integer id) {

    }
}
