package com.apibancario.project_banca.controller;

import com.apibancario.project_banca.model.dto.pagoprestamo.LoanPayRequestDto;
import com.apibancario.project_banca.model.dto.pagoprestamo.LoanPayResponseDto;
import com.apibancario.project_banca.service.PagoPrestamoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/loan-payments")
@Tag(name = "Pagos de Prestamo", description = "Operaciones acerca de los pagos de prestamo")
@AllArgsConstructor
public class PagoPrestamoController {

    private final PagoPrestamoService pagoPrestamoService;

    @GetMapping
    public ResponseEntity<List<LoanPayResponseDto>> getAllLoanPayments() {
        return ResponseEntity.ok( pagoPrestamoService.listAll() );
    }

    @GetMapping("/{id}")
    public ResponseEntity<LoanPayResponseDto> getLoanPayById(@PathVariable Integer id) {
        return ResponseEntity.ok( pagoPrestamoService.findById(id) );
    }

    @PostMapping
    public ResponseEntity<LoanPayResponseDto> saveLoanPay(
            @RequestBody @Valid LoanPayRequestDto loanPayRequestDto
            ){
        return ResponseEntity.status(HttpStatus.CREATED).body( pagoPrestamoService.save(loanPayRequestDto) );
    }
}
