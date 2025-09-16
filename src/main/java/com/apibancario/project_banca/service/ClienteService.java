package com.apibancario.project_banca.service;

import com.apibancario.project_banca.model.dto.cliente.ClientRequestDto;
import com.apibancario.project_banca.model.dto.cliente.ClientResponseDto;

import java.util.List;

public interface ClienteService {
    List<ClientResponseDto> listAll();
    ClientResponseDto findById(Integer id);
    ClientResponseDto save(ClientRequestDto clientRequestDto);
    ClientResponseDto update(Integer id, ClientRequestDto clientRequestDto);
    void delete(Integer id);
}
