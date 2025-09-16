package com.apibancario.project_banca.service.impl;

import com.apibancario.project_banca.model.dto.prestamo.LoanRequestDto;
import com.apibancario.project_banca.model.dto.prestamo.LoanResponseDto;
import com.apibancario.project_banca.service.PrestamoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrestamoServiceImpl implements PrestamoService {
    @Override
    public List<LoanResponseDto> listAll() {
        return List.of();
    }

    @Override
    public LoanResponseDto findById(Integer id) {
        return null;
    }

    @Override
    public LoanResponseDto save(LoanRequestDto loanRequestDto) {
        return null;
    }

    @Override
    public LoanResponseDto update(Integer id, LoanRequestDto loanRequestDto) {
        return null;
    }

    @Override
    public void delete(Integer id) {

    }
}
