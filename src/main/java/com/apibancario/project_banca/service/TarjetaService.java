package com.apibancario.project_banca.service;

import com.apibancario.project_banca.model.dto.tarjeta.CardRequestDto;
import com.apibancario.project_banca.model.dto.tarjeta.CardResponseDto;

import java.util.List;

public interface TarjetaService {
    List<CardResponseDto> listAll();
    CardResponseDto findById(Integer id);
    CardResponseDto save(CardRequestDto cardRequestDto);
    CardResponseDto update(Integer id, CardRequestDto cardRequestDto);
    void delete(Integer id);
}
