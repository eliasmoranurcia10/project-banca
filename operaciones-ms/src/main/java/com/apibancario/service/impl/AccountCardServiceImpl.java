package com.apibancario.service.impl;

import com.apibancario.feign.AccountFeignCard;
import com.apibancario.model.dto.cuenta.AccountResponseDto;
import com.apibancario.service.AccountCardService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AccountCardServiceImpl implements AccountCardService {

    private final AccountFeignCard accountFeignCard;

    @Override
    public AccountResponseDto getAccountById(Integer id) {
        return accountFeignCard.findById(id);
    }

    @Override
    public AccountResponseDto getAccountByNumberAccount(String numeroCuenta) {
        return accountFeignCard.findByAccountNumber(numeroCuenta);
    }
}
