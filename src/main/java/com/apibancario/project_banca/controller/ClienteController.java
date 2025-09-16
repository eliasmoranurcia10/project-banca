package com.apibancario.project_banca.controller;

import com.apibancario.project_banca.model.dto.cliente.ClientRequestDto;
import com.apibancario.project_banca.model.dto.cliente.ClientResponseDto;
import com.apibancario.project_banca.service.ClienteService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clients")
@Tag(name = "Clientes", description = "Operaciones acerca de los clientes")
@AllArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    @GetMapping
    public List<ClientResponseDto> getAllClients() {
        return clienteService.listAll();
    }

    @GetMapping("/{id}")
    public ClientResponseDto getClientById(@PathVariable Integer id) {
        return clienteService.findById(id);
    }

    @PostMapping
    public ClientResponseDto saveClient(@RequestBody ClientRequestDto clientRequestDto) {
        return clienteService.save(clientRequestDto);
    }

    @PutMapping("/{id}")
    public ClientResponseDto updateClient(
            @PathVariable Integer id,
            @RequestBody ClientRequestDto clientRequestDto
    ) {
        return clienteService.update(id, clientRequestDto);
    }

    @DeleteMapping("/{id}")
    public void deleteClient(@PathVariable Integer id) {
        clienteService.delete(id);
    }

}
