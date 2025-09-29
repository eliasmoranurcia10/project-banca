package com.apibancario.controller;

import com.apibancario.model.dto.cuenta.AccountRequestDto;
import com.apibancario.model.dto.cuenta.AccountResponseDto;
import com.apibancario.model.dto.cuenta.PasswordRequestDto;
import com.apibancario.service.CuentaService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
@Tag(name = "Cuentas", description = "Operaciones acerca de las cuentas")
@AllArgsConstructor
public class CuentaController {

    private final CuentaService cuentaService;

    @GetMapping
    public ResponseEntity<List<AccountResponseDto>> getAllAccounts() {
        return ResponseEntity.ok( cuentaService.listAll() );
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountResponseDto> getAccountById(@PathVariable Integer id) {
        return ResponseEntity.ok( cuentaService.findById(id) );
    }

    @GetMapping("/numero-cuenta/{numeroCuenta}")
    public ResponseEntity<AccountResponseDto> getAccountById(@PathVariable("numeroCuenta") String numeroCuenta) {
        return ResponseEntity.ok( cuentaService.findByNumberAccount(numeroCuenta) );
    }

    @PostMapping
    public ResponseEntity<AccountResponseDto> saveAccount(@RequestBody @Valid AccountRequestDto accountRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cuentaService.save(accountRequestDto));
    }

    @PatchMapping("/{id}/cambiar-clave")
    public ResponseEntity<AccountResponseDto> updatePasswordAccount(
            @PathVariable Integer id,
            @RequestBody @Valid PasswordRequestDto passwordRequestDto
    ){
        return ResponseEntity.ok( cuentaService.updatePassword(id, passwordRequestDto) );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Integer id) {
        cuentaService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
