package com.apibancario.service;

import com.apibancario.model.dto.tarjeta.CardPinRequestDto;
import com.apibancario.model.dto.tarjeta.CardRequestDto;
import com.apibancario.model.dto.tarjeta.CardResponseDto;

import java.util.List;

public interface TarjetaService {
    List<CardResponseDto> listAll();
    CardResponseDto findById(Integer id);
    CardResponseDto save(CardRequestDto cardRequestDto);
    CardResponseDto updatePinTarjeta(Integer id, CardPinRequestDto cardPinRequestDto);
    void delete(Integer id);
}
