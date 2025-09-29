package com.apibancario.controller;

import com.apibancario.model.dto.tarjeta.CardPinRequestDto;
import com.apibancario.model.dto.tarjeta.CardRequestDto;
import com.apibancario.model.dto.tarjeta.CardResponseDto;
import com.apibancario.service.TarjetaService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cards")
@Tag(name = "Tarjetas", description = "Operaciones acerca de las tarjetas")
@AllArgsConstructor
public class TarjetaController {

    private final TarjetaService tarjetaService;

    @GetMapping
    public ResponseEntity<List<CardResponseDto>> getAllCards() {
        return ResponseEntity.ok( tarjetaService.listAll() );
    }

    @GetMapping("/{id}")
    public ResponseEntity<CardResponseDto> getCardById(@PathVariable Integer id) {
        return ResponseEntity.ok( tarjetaService.findById(id) );
    }

    @PostMapping
    public ResponseEntity<CardResponseDto> saveCard(@RequestBody @Valid CardRequestDto cardRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body( tarjetaService.save(cardRequestDto) );
    }

    @PatchMapping("/{id}/cambiar-pin")
    public ResponseEntity<CardResponseDto> updatePinCard(
            @PathVariable Integer id,
            @RequestBody @Valid CardPinRequestDto cardPinRequestDto
    ) {
        return ResponseEntity.ok( tarjetaService.updatePinTarjeta(id,cardPinRequestDto) );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCard(@PathVariable Integer id) {
        tarjetaService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
