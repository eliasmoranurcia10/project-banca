package com.apibancario.service.impl;

import com.apibancario.feign.ClientFeignLoan;
import com.apibancario.model.dto.cliente.ClientRequestDto;
import com.apibancario.model.dto.cliente.ClientResponseDto;
import com.apibancario.service.ClientLoanService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ClientLoanServiceImpl implements ClientLoanService {

    private final ClientFeignLoan clientFeignLoan;

    @Override
    public List<ClientResponseDto> listClients() {
        return clientFeignLoan.listAll();
    }

    @Override
    public ClientResponseDto getClientById(Integer id) {
        return clientFeignLoan.findById(id);
    }

    @Override
    public ClientResponseDto saveClient(ClientRequestDto clientRequestDto) {
        return clientFeignLoan.save(clientRequestDto);
    }

    @Override
    public ClientResponseDto updateClient(Integer id, ClientRequestDto clientRequestDto) {
        return clientFeignLoan.update(id, clientRequestDto);
    }

    @Override
    public void deleteClient(Integer id) {
        clientFeignLoan.delete(id);
    }

}
