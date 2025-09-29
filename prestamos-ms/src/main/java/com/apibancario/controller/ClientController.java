package com.apibancario.controller;

import com.apibancario.model.dto.cliente.ClientRequestDto;
import com.apibancario.model.dto.cliente.ClientResponseDto;
import com.apibancario.service.ClientLoanService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clients")
@Tag(name = "Clientes", description = "Operaciones acerca de los clientes desde microservicio")
@AllArgsConstructor
public class ClientController {

    private final ClientLoanService clientLoanService;

    @GetMapping
    public ResponseEntity<List<ClientResponseDto>> getAllClients() {
        return ResponseEntity.ok( clientLoanService.listClients() );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientResponseDto> getClientById(@PathVariable Integer id) {
        return ResponseEntity.ok(clientLoanService.getClientById(id));
    }

    @PostMapping
    public ResponseEntity<ClientResponseDto> saveClient(@RequestBody @Valid ClientRequestDto clientRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clientLoanService.saveClient(clientRequestDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientResponseDto> updateClient(@PathVariable Integer id, @RequestBody @Valid ClientRequestDto clientRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clientLoanService.updateClient(id, clientRequestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Integer id) {
        clientLoanService.deleteClient(id);
        return ResponseEntity.noContent().build();
    }

}
