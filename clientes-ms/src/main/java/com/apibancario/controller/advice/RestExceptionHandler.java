package com.apibancario.controller.advice;

import com.apibancario.exception.BadRequestException;
import com.apibancario.exception.InternalServerErrorException;
import com.apibancario.exception.ResourceNotFoundException;
import com.apibancario.model.dto.errordetail.ErrorDetailDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class RestExceptionHandler {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetailDto> handleNotFound(ResourceNotFoundException ex) {
        return buildResponse("not-found" , ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorDetailDto> handleBadRequest(BadRequestException ex) {
        return buildResponse( "bad-request", ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InternalServerErrorException.class)
    public ResponseEntity<ErrorDetailDto> handleInternalServer(InternalServerErrorException ex) {
        return buildResponse( "internal-server", ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetailDto> handeGeneral(Exception ex) {
        return buildResponse("general-exception",":( Error inesperado: "+ ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErrorDetailDto>> handleException(MethodArgumentNotValidException ex) {

        List<ErrorDetailDto> errores = new ArrayList<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> errores.add(new ErrorDetailDto(
                error.getField(),
                error.getDefaultMessage(),
                LocalDateTime.now().format(FORMATTER)
                )
        ));

        return ResponseEntity.badRequest().body(errores);
    }

    private ResponseEntity<ErrorDetailDto> buildResponse(String type ,String message, HttpStatus status) {
        ErrorDetailDto errorDetailDto = new ErrorDetailDto(type, message ,LocalDateTime.now().format(FORMATTER));
        return ResponseEntity.status(status).body(errorDetailDto);
    }
}
