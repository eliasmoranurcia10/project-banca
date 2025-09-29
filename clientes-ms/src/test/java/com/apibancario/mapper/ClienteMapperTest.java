package com.apibancario.mapper;

import com.apibancario.model.dto.cliente.ClientRequestDto;
import com.apibancario.model.dto.cliente.ClientResponseDto;
import com.apibancario.model.entity.Cliente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ClienteMapperTest {

    private final ClienteMapper clienteMapper = Mappers.getMapper(ClienteMapper.class);
    private ClientRequestDto clientRequestDto;

    @BeforeEach
    void setUp() {
        clientRequestDto = new ClientRequestDto(
                "Yoexer Elias",
                "Moran Urcia",
                "74755856",
                "elias@gmail.com"
        );
    }

    @Test
    void testToCliente() {
        Cliente cliente = clienteMapper.toCliente(clientRequestDto);

        assertNotNull(cliente);
        assertEquals(clientRequestDto.name(), cliente.getNombre());
        assertEquals(clientRequestDto.lastName(), cliente.getApellido());
        assertEquals(clientRequestDto.dni(), cliente.getDni());
        assertEquals(clientRequestDto.email(), cliente.getEmail());
    }

    @Test
    void testToClienteNull() {
        Cliente cliente = clienteMapper.toCliente(null);
        assertNull(cliente);
    }

    @Test
    void testUpdateClienteFromDto() {
        Cliente cliente = new Cliente(1,"Elias", "Moran", "75484848", "elias@gmail.com",new ArrayList<>());

        clienteMapper.updateClienteFromDto(clientRequestDto, cliente);

        assertEquals(1, cliente.getIdCliente());
        assertEquals(clientRequestDto.name(), cliente.getNombre());
        assertEquals(clientRequestDto.lastName(), cliente.getApellido());
        assertEquals(clientRequestDto.dni(), cliente.getDni());
        assertEquals(clientRequestDto.email(), cliente.getEmail());
    }

    @Test
    void testToClientResponseDto() {
        Cliente cliente = new Cliente(1,"Elias", "Moran", "75484848", "elias@gmail.com",new ArrayList<>());

        ClientResponseDto clientResponseDto = clienteMapper.toClientResponseDto(cliente);

        assertEquals(cliente.getIdCliente(), clientResponseDto.clientId());
        assertEquals(cliente.getNombre(), clientResponseDto.name());
        assertEquals(cliente.getApellido(), clientResponseDto.lastName());
        assertEquals(cliente.getEmail(), clientResponseDto.email());
    }

    @Test
    void testToClientResponseDtoNull() {
        ClientResponseDto clientResponseDto = clienteMapper.toClientResponseDto(null);
        assertNull(clientResponseDto);
    }

    @Test
    void testToClientsResponseDto() {
        List<Cliente> clientes = List.of(
                new Cliente(1,"Elias", "Moran", "75484848", "elias@gmail.com",new ArrayList<>())
        );
        List<ClientResponseDto> clientsResponseDto = clienteMapper.toClientsResponseDto(clientes);

        assertEquals(clientes.getFirst().getIdCliente(), clientsResponseDto.getFirst().clientId());
        assertEquals(clientes.getFirst().getNombre(), clientsResponseDto.getFirst().name());
        assertEquals(clientes.getFirst().getApellido(), clientsResponseDto.getFirst().lastName());
        assertEquals(clientes.getFirst().getEmail(), clientsResponseDto.getFirst().email());
    }

    @Test
    void testToClientsResponseDtoNull() {
        List<ClientResponseDto> clientsResponseDto = clienteMapper.toClientsResponseDto(null);
        assertNull(clientsResponseDto);
    }

}
