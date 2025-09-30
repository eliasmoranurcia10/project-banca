package com.apibancario.feign;

import com.apibancario.model.dto.cuenta.AccountResponseDto;
import com.apibancario.model.dto.cuenta.UpdateSaldoRequestDto;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "clientes-ms")
public interface AccountFeignCard {

    @GetMapping("/accounts/{id}")
    AccountResponseDto findById(@PathVariable("id") Integer id);

    @GetMapping("/accounts/numero-cuenta/{numeroCuenta}")
    AccountResponseDto findByAccountNumber(@PathVariable("numeroCuenta") String numeroCuenta);

    @PutMapping("/accounts/actualizar-saldo/{id}")
    AccountResponseDto updateAccountBalance(@PathVariable Integer id, @RequestBody UpdateSaldoRequestDto updateSaldoRequestDto);
}
