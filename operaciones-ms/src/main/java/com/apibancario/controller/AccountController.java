package com.apibancario.controller;

import com.apibancario.model.dto.cuenta.AccountResponseDto;
import com.apibancario.service.AccountCardService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/accounts")
@Tag(name = "Cuentas", description = "Operaciones acerca de las cuentas en el microservicio")
@AllArgsConstructor
public class AccountController {

    private final AccountCardService accountCardService;

    @GetMapping("/{id}")
    public ResponseEntity<AccountResponseDto> getAccountById(@PathVariable Integer id) {
        return ResponseEntity.ok( accountCardService.getAccountById(id) );
    }

    @GetMapping("/numero-cuenta/{numeroCuenta}")
    public ResponseEntity<AccountResponseDto> getAccountByNumber(@PathVariable String numeroCuenta) {
        return ResponseEntity.ok( accountCardService.getAccountByNumberAccount(numeroCuenta) );
    }
}
