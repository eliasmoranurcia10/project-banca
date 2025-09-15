package com.apibancario.project_banca.mapper;

import com.apibancario.project_banca.model.dto.cliente.ClientRequestDto;
import com.apibancario.project_banca.model.dto.cliente.ClientResponseDto;
import com.apibancario.project_banca.model.entity.Cliente;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClienteMapper {

    @Mapping(target = "idCliente", ignore = true)
    @Mapping(target = "nombre", source = "name")
    @Mapping(target = "apellido", source = "lastName")
    @Mapping(target = "cuentas", ignore = true)
    @Mapping(target = "prestamos", ignore = true)
    Cliente toCliente(ClientRequestDto clientRequestDto);
    List<Cliente> toClientes(List<ClientRequestDto> clientsRequestDto);

    @Mapping(target = "clientId", source = "idCliente")
    @Mapping(target = "name", source = "nombre")
    @Mapping(target = "lastName", source = "apellido")
    ClientResponseDto toClientResponseDto(Cliente cliente);
    List<ClientResponseDto> toClientsResponseDto(List<Cliente> clientes);
}
