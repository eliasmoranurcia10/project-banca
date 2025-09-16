package com.apibancario.project_banca.service;

import com.apibancario.project_banca.model.dto.prestamo.LoanRequestDto;
import com.apibancario.project_banca.model.dto.prestamo.LoanResponseDto;

import java.util.List;

public interface PrestamoService {
    List<LoanResponseDto> listAll();
    LoanResponseDto findById(Integer id);
    LoanResponseDto save(LoanRequestDto loanRequestDto);
    LoanResponseDto update(Integer id, LoanRequestDto loanRequestDto);
    void delete(Integer id);
}