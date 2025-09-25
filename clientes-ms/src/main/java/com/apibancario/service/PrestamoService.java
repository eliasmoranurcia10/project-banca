package com.apibancario.service;

import com.apibancario.project_banca.model.dto.prestamo.LoanRequestDto;
import com.apibancario.project_banca.model.dto.prestamo.LoanResponseDto;
import com.apibancario.project_banca.model.dto.prestamo.StatusLoanRequestDto;

import java.util.List;

public interface PrestamoService {
    List<LoanResponseDto> listAll();
    LoanResponseDto findById(Integer id);
    LoanResponseDto save(LoanRequestDto loanRequestDto);
    LoanResponseDto updateStatusLoan(Integer id, StatusLoanRequestDto statusLoanRequestDto);
}