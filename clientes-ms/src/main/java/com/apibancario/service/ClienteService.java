package com.apibancario.service;

import com.apibancario.model.dto.cliente.ClientRequestDto;
import com.apibancario.model.dto.cliente.ClientResponseDto;

import java.util.List;

public interface ClienteService {
    List<ClientResponseDto> listAll();
    ClientResponseDto findById(Integer id);
    ClientResponseDto save(ClientRequestDto clientRequestDto);
    ClientResponseDto update(Integer id, ClientRequestDto clientRequestDto);
    void delete(Integer id);
}
