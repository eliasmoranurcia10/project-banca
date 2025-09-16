package com.apibancario.project_banca.service.impl;

import com.apibancario.project_banca.model.dto.pagoprestamo.LoanPayRequestDto;
import com.apibancario.project_banca.model.dto.pagoprestamo.LoanPayResponseDto;
import com.apibancario.project_banca.service.PagoPrestamoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PagoPrestamoServiceImpl implements PagoPrestamoService {
    @Override
    public List<LoanPayResponseDto> listAll() {
        return List.of();
    }

    @Override
    public LoanPayResponseDto findById(Integer id) {
        return null;
    }

    @Override
    public LoanPayResponseDto save(LoanPayRequestDto loanPayRequestDto) {
        return null;
    }

    @Override
    public LoanPayResponseDto update(Integer id, LoanPayRequestDto loanPayRequestDto) {
        return null;
    }

    @Override
    public void delete(Integer id) {

    }
}
