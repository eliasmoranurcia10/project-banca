package com.apibancario.project_banca.mapper;

import com.apibancario.project_banca.model.dto.transaccion.TransactionRequestDto;
import com.apibancario.project_banca.model.dto.transaccion.TransactionResponseDto;
import com.apibancario.project_banca.model.entity.Transaccion;
import com.apibancario.project_banca.model.enums.TipoTransaccion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Mapper(componentModel = "spring", uses = {TarjetaMapper.class, CuentaMapper.class})
public interface TransaccionMapper {

    @Mapping(target = "idTransaccion", ignore = true)
    @Mapping(target = "tipoTransaccion", expression = "java(transactionRequestDto.transactionType().name())") // Enum -> String
    @Mapping(target = "monto", source = "amount")
    @Mapping(target = "fecha", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "tarjeta", ignore = true)
    @Mapping(target = "cuentaDestino", ignore = true)
    Transaccion toTransaccion(TransactionRequestDto transactionRequestDto);
    List<Transaccion> toTransacciones(List<TransactionRequestDto> transactionsRequestDto);

    @Mapping(target = "transactionId", source = "idTransaccion")
    @Mapping(target = "transactionType", source = "tipoTransaccion", qualifiedByName = "tipoTransaccionToTransactionType")
    @Mapping(target = "amount", source = "monto")
    @Mapping(target = "date", source = "fecha", qualifiedByName = "fechaToDate")
    @Mapping(target = "cardResponseDto", source = "tarjeta")
    @Mapping(target = "recipientAccountResponseDto", source = "cuentaDestino")
    TransactionResponseDto toTransactionResponseDto(Transaccion transaccion);
    List<TransactionResponseDto> toTransactionsResponseDto(List<Transaccion> transacciones);

    @Named("fechaToDate")
    default String fechaToDate(LocalDateTime fecha) {
        return fecha==null ? null : fecha.format(
                DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")
        );
    }

    @Named("tipoTransaccionToTransactionType")
    default TipoTransaccion tipoTransaccionToTransactionType(String tipoTransaccion) {
        return tipoTransaccion==null ? null : switch (tipoTransaccion.toUpperCase()) {
            case "DEPOSITO" -> TipoTransaccion.DEPOSITO;
            case "RETIRO" -> TipoTransaccion.RETIRO;
            case "TRANSFERENCIA" -> TipoTransaccion.TRANSFERENCIA;
            case "PAGO" -> TipoTransaccion.PAGO;
            default -> null;
        };
    }
}
