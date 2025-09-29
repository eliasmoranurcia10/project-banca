package com.apibancario.controller;

import com.apibancario.model.dto.transaccion.TransactionRequestDto;
import com.apibancario.model.dto.transaccion.TransactionResponseDto;
import com.apibancario.service.TransaccionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
@Tag(name = "Transacciones", description = "Operaciones acerca de las transacciones")
@AllArgsConstructor
public class TransaccionController {

    private final TransaccionService transaccionService;

    @GetMapping
    public ResponseEntity<List<TransactionResponseDto>> getAllTransactions() {
        return ResponseEntity.ok( transaccionService.listAll() );
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionResponseDto> getTransactionById(@PathVariable Integer id) {
        return ResponseEntity.ok( transaccionService.findById(id) );
    }

    @PostMapping
    public ResponseEntity<TransactionResponseDto> saveTransaction(
        @RequestBody @Valid TransactionRequestDto transactionRequestDto
    ){
        return ResponseEntity.status(HttpStatus.CREATED).body( transaccionService.save(transactionRequestDto) );
    }

}
