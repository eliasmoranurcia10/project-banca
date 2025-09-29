package com.apibancario.feign;

import com.apibancario.model.dto.cuenta.AccountResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "clientes-ms")
public interface AccountFeignCard {

    @GetMapping("/accounts/{id}")
    AccountResponseDto findById(@PathVariable("id") Integer id);

    @GetMapping("/accounts/numero-cuenta/{numeroCuenta}")
    AccountResponseDto findByAccountNumber(@PathVariable("numeroCuenta") String numeroCuenta);

}
