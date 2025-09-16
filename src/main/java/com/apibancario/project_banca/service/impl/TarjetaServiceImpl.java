package com.apibancario.project_banca.service.impl;

import com.apibancario.project_banca.model.dto.tarjeta.CardRequestDto;
import com.apibancario.project_banca.model.dto.tarjeta.CardResponseDto;
import com.apibancario.project_banca.service.TarjetaService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TarjetaServiceImpl implements TarjetaService {
    @Override
    public List<CardResponseDto> listAll() {
        return List.of();
    }

    @Override
    public CardResponseDto findById(Integer id) {
        return null;
    }

    @Override
    public CardResponseDto save(CardRequestDto cardRequestDto) {
        return null;
    }

    @Override
    public CardResponseDto update(Integer id, CardRequestDto cardRequestDto) {
        return null;
    }

    @Override
    public void delete(Integer id) {

    }
}
