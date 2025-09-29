package com.apibancario.mapper;

import com.apibancario.model.dto.cliente.ClientRequestDto;
import com.apibancario.model.dto.cliente.ClientResponseDto;
import com.apibancario.model.entity.Cliente;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClienteMapper {

    @Mapping(target = "idCliente", ignore = true)
    @Mapping(target = "nombre", source = "name")
    @Mapping(target = "apellido", source = "lastName")
    @Mapping(target = "cuentas", ignore = true)
    Cliente toCliente(ClientRequestDto clientRequestDto);

    @InheritConfiguration(name = "toCliente")
    void updateClienteFromDto(ClientRequestDto clientRequestDto, @MappingTarget Cliente cliente);

    @Mapping(target = "clientId", source = "idCliente")
    @Mapping(target = "name", source = "nombre")
    @Mapping(target = "lastName", source = "apellido")
    ClientResponseDto toClientResponseDto(Cliente cliente);
    List<ClientResponseDto> toClientsResponseDto(List<Cliente> clientes);


}
