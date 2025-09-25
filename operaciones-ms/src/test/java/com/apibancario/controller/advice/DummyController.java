package com.apibancario.controller.advice;

import com.apibancario.project_banca.exception.BadRequestException;
import com.apibancario.project_banca.exception.InternalServerErrorException;
import com.apibancario.project_banca.exception.ResourceNotFoundException;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class DummyController {
    @GetMapping("/notfound")
    public String notFound() {
        throw new ResourceNotFoundException("Recurso no encontrado");
    }

    @GetMapping("/badrequest")
    public String badRequest() {
        throw new BadRequestException("Petición inválida");
    }

    @GetMapping("/internal")
    public String internal() {
        throw new InternalServerErrorException("Error interno");
    }

    @GetMapping("/general-error")
    public String generalError() {
        throw new RuntimeException("Error general");
    }
}
