package com.apibancario.service;

import com.apibancario.model.dto.cliente.ClientRequestDto;
import com.apibancario.model.dto.cliente.ClientResponseDto;

import java.util.List;

public interface ClientLoanService {
    List<ClientResponseDto> listClients();
    ClientResponseDto getClientById(Integer id);
    ClientResponseDto saveClient(ClientRequestDto clientRequestDto);
    ClientResponseDto updateClient(Integer id, ClientRequestDto clientRequestDto);
    void deleteClient(Integer id);
}
