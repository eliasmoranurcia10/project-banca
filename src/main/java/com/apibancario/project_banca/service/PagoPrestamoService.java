package com.apibancario.project_banca.service;

import com.apibancario.project_banca.model.dto.pagoprestamo.LoanPayRequestDto;
import com.apibancario.project_banca.model.dto.pagoprestamo.LoanPayResponseDto;

import java.util.List;

public interface PagoPrestamoService {
    List<LoanPayResponseDto> listAll();
    LoanPayResponseDto findById(Integer id);
    LoanPayResponseDto save(LoanPayRequestDto loanPayRequestDto);
}
