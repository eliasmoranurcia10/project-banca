package com.apibancario.project_banca.service.impl;

import com.apibancario.project_banca.mapper.ClienteMapper;
import com.apibancario.project_banca.model.dto.cliente.ClientRequestDto;
import com.apibancario.project_banca.model.dto.cliente.ClientResponseDto;
import com.apibancario.project_banca.model.entity.Cliente;
import com.apibancario.project_banca.repository.ClienteRepository;
import com.apibancario.project_banca.service.ClienteService;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;
    private final ClienteMapper clienteMapper;

    @Override
    public List<ClientResponseDto> listAll() {
        return clienteMapper.toClientsResponseDto(clienteRepository.findAll());
    }

    @Override
    public ClientResponseDto findById(Integer id) {
        Cliente cliente = clienteRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Error al buscar id")
        );
        return clienteMapper.toClientResponseDto(cliente);
    }

    @Override
    public ClientResponseDto save(ClientRequestDto clientRequestDto) {
        try{
            Cliente cliente = clienteMapper.toCliente(clientRequestDto);
            return clienteMapper.toClientResponseDto( clienteRepository.save(cliente) );
        } catch (Exception exception) {
            throw new RuntimeException("Error inesperado");
        }
    }

    @Override
    public ClientResponseDto update(Integer id, ClientRequestDto clientRequestDto) {
        if(id==null) throw new RuntimeException("El id no puede ser nulo");
        return clienteRepository.findById(id)
                .map( cliente -> {
                    clienteMapper.updateClienteFromDto(clientRequestDto, cliente);
                    return clienteMapper.toClientResponseDto(clienteRepository.save(cliente));
                })
                .orElseThrow(
                        () -> new RuntimeException("Error al buscar id")
                );
    }

    @Override
    public void delete(Integer id) {
        if(id==null) throw new RuntimeException("El id no puede ser nulo");
        Cliente cliente = clienteRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Error al buscar id")
        );
        try {
            clienteRepository.delete(cliente);
        } catch (DataIntegrityViolationException exception) {
            throw new RuntimeException("No se puede eliminar");
        }
    }
}
