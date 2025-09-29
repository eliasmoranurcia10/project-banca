package com.apibancario.service;

import com.apibancario.model.dto.pagoprestamo.LoanPayRequestDto;
import com.apibancario.model.dto.pagoprestamo.LoanPayResponseDto;

import java.util.List;

public interface PagoPrestamoService {
    List<LoanPayResponseDto> listAll();
    LoanPayResponseDto findById(Integer id);
    LoanPayResponseDto save(LoanPayRequestDto loanPayRequestDto);
}
