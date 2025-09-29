package com.apibancario.service.impl;

import com.apibancario.exception.BadRequestException;
import com.apibancario.exception.InternalServerErrorException;
import com.apibancario.exception.ResourceNotFoundException;
import com.apibancario.mapper.ClienteMapper;
import com.apibancario.model.dto.cliente.ClientRequestDto;
import com.apibancario.model.dto.cliente.ClientResponseDto;
import com.apibancario.model.entity.Cliente;
import com.apibancario.repository.ClienteRepository;
import com.apibancario.service.ClienteService;
import lombok.AllArgsConstructor;
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
        if(id==null || id<=0) throw new BadRequestException("El id es incorrecto");
        Cliente cliente = clienteRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("No se encontró al cliente con id: "+id)
        );
        return clienteMapper.toClientResponseDto(cliente);
    }

    @Override
    public ClientResponseDto save(ClientRequestDto clientRequestDto) {
        Cliente cliente = clienteMapper.toCliente(clientRequestDto);
        try{
            return clienteMapper.toClientResponseDto( clienteRepository.save(cliente) );
        } catch (Exception exception) {
            throw new BadRequestException("Error al guardar el cliente, ingresar datos correctos");
        }
    }

    @Override
    public ClientResponseDto update(Integer id, ClientRequestDto clientRequestDto) {
        if(id==null || id<=0) throw new BadRequestException("El id es incorrecto");

        return clienteRepository.findById(id)
                .map( cliente -> {
                    clienteMapper.updateClienteFromDto(clientRequestDto, cliente);
                    try {
                        return clienteMapper.toClientResponseDto(clienteRepository.save(cliente));
                    } catch (Exception ex) {
                        throw new BadRequestException("Error al actualizar cliente, ingresar datos correctos");
                    }
                })
                .orElseThrow(
                        () -> new ResourceNotFoundException("No se encontró al cliente con id: "+id)
                );
    }

    @Override
    public void delete(Integer id) {
        if(id==null || id<=0) throw new BadRequestException("El id es incorrecto");
        Cliente cliente = clienteRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("No se encontró al cliente con id: "+id)
        );
        try {
            clienteRepository.delete(cliente);
        } catch (Exception exception) {
            throw new InternalServerErrorException("Error inesperado al eliminar al cliente");
        }
    }
}
