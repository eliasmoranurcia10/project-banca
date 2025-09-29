package com.apibancario.controller;

import com.apibancario.model.dto.prestamo.LoanRequestDto;
import com.apibancario.model.dto.prestamo.LoanResponseDto;
import com.apibancario.model.dto.prestamo.StatusLoanRequestDto;
import com.apibancario.service.PrestamoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/loans")
@Tag(name = "Prestamos", description = "Operaciones acerca de los préstamos")
@AllArgsConstructor
public class PrestamoController {

    private final PrestamoService prestamoService;

    @GetMapping
    public ResponseEntity<List<LoanResponseDto>> getAllLoans() {
        return ResponseEntity.ok( prestamoService.listAll() );
    }

    @GetMapping("/{id}")
    public ResponseEntity<LoanResponseDto> getLoanById(@PathVariable Integer id) {
        return ResponseEntity.ok( prestamoService.findById(id) );
    }

    @PostMapping
    public ResponseEntity<LoanResponseDto> saveLoan(@RequestBody @Valid LoanRequestDto loanRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body( prestamoService.save(loanRequestDto) );
    }

    @PatchMapping("/{id}")
    public ResponseEntity<LoanResponseDto> updateStatusLoan(
            @PathVariable Integer id,
            @RequestBody @Valid StatusLoanRequestDto statusLoanRequestDto
    ) {
        return ResponseEntity.ok( prestamoService.updateStatusLoan(id, statusLoanRequestDto) );
    }
}
