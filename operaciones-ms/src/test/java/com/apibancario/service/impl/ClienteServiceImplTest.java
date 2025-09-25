package com.apibancario.service.impl;

import com.apibancario.project_banca.exception.BadRequestException;
import com.apibancario.project_banca.exception.InternalServerErrorException;
import com.apibancario.project_banca.exception.ResourceNotFoundException;
import com.apibancario.project_banca.mapper.ClienteMapper;
import com.apibancario.project_banca.model.dto.cliente.ClientRequestDto;
import com.apibancario.project_banca.model.dto.cliente.ClientResponseDto;
import com.apibancario.project_banca.model.entity.Cliente;
import com.apibancario.project_banca.repository.ClienteRepository;
import com.apibancario.project_banca.service.ClienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClienteServiceImplTest {
    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private ClienteMapper clienteMapper;

    @InjectMocks
    private ClienteServiceImpl clienteService;

    private Cliente cliente;
    private ClientRequestDto clientRequestDto;
    private ClientResponseDto clientResponseDto;

    @BeforeEach
    void setUp() {
        cliente = new Cliente(1,"Elias", "Moran", "75484848", "elias@gmail.com",new ArrayList<>(), new ArrayList<>());
        clientRequestDto = new ClientRequestDto("Elias", "Moran", "75484848", "elias@gmail.com");
        clientResponseDto = new ClientResponseDto(1,"Elias", "Moran","elias@gmail.com");
    }

    @Test
    void testListAll_Success() {
        when(clienteRepository.findAll()).thenReturn(List.of(cliente));
        when(clienteMapper.toClientsResponseDto(List.of(cliente))).thenReturn(List.of(clientResponseDto));

        List<ClientResponseDto> clientsResponseDto = clienteService.listAll();

        assertNotNull(clientsResponseDto);
        assertEquals(1, clientsResponseDto.getFirst().clientId());
        assertEquals("Elias", clientsResponseDto.getFirst().name());
        assertEquals(1, clientsResponseDto.size());
        verify(clienteRepository, times(1)).findAll();
    }

    @Test
    void testFindById_BadRequestExceptionZero() {
        BadRequestException ex = assertThrows(BadRequestException.class, () -> clienteService.findById(0));
        assertEquals("El id es incorrecto", ex.getMessage());
    }

    @Test
    void testFindById_BadRequestExceptionNull() {
        BadRequestException ex = assertThrows(BadRequestException.class, () -> clienteService.findById(null));
        assertEquals("El id es incorrecto", ex.getMessage());
    }

    @Test
    void testFindById_ResourceNotFoundException() {
        Integer id = 99;
        when(clienteRepository.findById(id)).thenReturn(Optional.empty());
        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () -> clienteService.findById(id));
        assertEquals("No se encontró al cliente con id: "+id, ex.getMessage());
    }

    @Test
    void testFindById_Success() {
        Integer id = 1;
        when(clienteRepository.findById(id)).thenReturn(Optional.of(cliente));
        when(clienteMapper.toClientResponseDto(cliente)).thenReturn(clientResponseDto);

        ClientResponseDto result = clienteService.findById(id);

        assertNotNull(result);
        assertEquals(1, result.clientId());
        assertEquals("Elias", result.name());
        verify(clienteRepository, times(1)).findById(id);
    }

    @Test
    void testSave_BadRequestException() {
        when(clienteMapper.toCliente(clientRequestDto)).thenReturn(cliente);
        when(clienteRepository.save(cliente)).thenThrow(new RuntimeException("DB error"));

        BadRequestException ex = assertThrows(BadRequestException.class, () -> clienteService.save(clientRequestDto));

        assertEquals("Error al guardar el cliente, ingresar datos correctos", ex.getMessage());
    }

    @Test
    void testSave_Success() {
        when(clienteMapper.toCliente(clientRequestDto)).thenReturn(cliente);
        when(clienteRepository.save(cliente)).thenReturn(cliente);
        when(clienteMapper.toClientResponseDto(cliente)).thenReturn(clientResponseDto);

        ClientResponseDto result = clienteService.save(clientRequestDto);

        assertNotNull(result);
        assertEquals("Elias", result.name());
        verify(clienteMapper, times(1)).toCliente(clientRequestDto);
    }

    @Test
    void testUpdate_BadRequestExceptionZero() {
        BadRequestException ex = assertThrows(BadRequestException.class, () -> clienteService.update(0, clientRequestDto));
        assertEquals("El id es incorrecto", ex.getMessage());
    }

    @Test
    void testUpdate_BadRequestExceptionNull() {
        BadRequestException ex = assertThrows(BadRequestException.class, () -> clienteService.update(null, clientRequestDto));
        assertEquals("El id es incorrecto", ex.getMessage());
    }

    @Test
    void testUpdate_ResourceNotFoundException() {
        Integer id = 99;
        when(clienteRepository.findById(id)).thenReturn(Optional.empty());
        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () -> clienteService.update(id, clientRequestDto));
        assertEquals("No se encontró al cliente con id: "+id, ex.getMessage());
    }

    @Test
    void testUpdate_BadRequestExceptionIncorrectData() {
        when(clienteRepository.findById(1)).thenReturn(Optional.of(cliente));
        doNothing().when(clienteMapper).updateClienteFromDto(clientRequestDto, cliente);
        when(clienteRepository.save(cliente)).thenThrow(new RuntimeException("DB error"));

        BadRequestException ex = assertThrows(BadRequestException.class, () -> clienteService.update(1, clientRequestDto));

        assertEquals("Error al actualizar cliente, ingresar datos correctos", ex.getMessage());
    }

    @Test
    void testDelete_BadRequestExceptionZero() {
        BadRequestException ex = assertThrows(BadRequestException.class, () -> clienteService.delete(0));
        assertEquals("El id es incorrecto", ex.getMessage());
    }

    @Test
    void testDelete_BadRequestExceptionNull() {
        BadRequestException ex = assertThrows(BadRequestException.class, () -> clienteService.delete(null));
        assertEquals("El id es incorrecto", ex.getMessage());
    }

    @Test
    void testDelete_ResourceNotFoundException() {
        Integer id = 99;
        when(clienteRepository.findById(id)).thenReturn(Optional.empty());
        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () -> clienteService.delete(id));
        assertEquals("No se encontró al cliente con id: "+id, ex.getMessage());
    }

    @Test
    void testDelete_InternalServerErrorException() {
        when(clienteRepository.findById(1)).thenReturn(Optional.of(cliente));
        doThrow(new RuntimeException("DB error")).when(clienteRepository).delete(cliente);

        InternalServerErrorException ex = assertThrows(InternalServerErrorException.class, () -> clienteService.delete(1));
        assertEquals("Error inesperado al eliminar al cliente", ex.getMessage());
    }

    @Test
    void testDelete_Success() {
        when(clienteRepository.findById(1)).thenReturn(Optional.of(cliente));
        doNothing().when(clienteRepository).delete(cliente);

        clienteService.delete(1);

        verify(clienteRepository, times(1)).delete(cliente);

    }

}
