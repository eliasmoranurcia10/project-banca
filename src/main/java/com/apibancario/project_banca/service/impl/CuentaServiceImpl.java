package com.apibancario.project_banca.service.impl;

import com.apibancario.project_banca.mapper.CuentaMapper;
import com.apibancario.project_banca.model.dto.cuenta.AccountRequestDto;
import com.apibancario.project_banca.model.dto.cuenta.AccountResponseDto;
import com.apibancario.project_banca.repository.CuentaRepository;
import com.apibancario.project_banca.service.CuentaService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CuentaServiceImpl implements CuentaService {

    private final CuentaRepository cuentaRepository;
    private final CuentaMapper cuentaMapper;

    @Override
    public List<AccountResponseDto> listAll() {
        return cuentaMapper.toAccountsResponseDto( cuentaRepository.findAll() );
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
