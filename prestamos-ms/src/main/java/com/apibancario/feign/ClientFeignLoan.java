package com.apibancario.feign;

import com.apibancario.model.dto.cliente.ClientRequestDto;
import com.apibancario.model.dto.cliente.ClientResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "clientes-ms")
public interface ClientFeignLoan {

    @GetMapping("/clients")
    List<ClientResponseDto> listAll();

    @GetMapping("clients/{id}")
    ClientResponseDto findById(@PathVariable("id") Integer id);

    @PostMapping("/clients")
    ClientResponseDto save(@RequestBody ClientRequestDto clientRequestDto);

    @PutMapping("/clients/{id}")
    ClientResponseDto update(@PathVariable("id") Integer id, @RequestBody ClientRequestDto clientRequestDto);

    @DeleteMapping("/clients/{id}")
    void delete(@PathVariable("id") Integer id);
}
