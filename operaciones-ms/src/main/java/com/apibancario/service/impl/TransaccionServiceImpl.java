package com.apibancario.service.impl;

import com.apibancario.exception.BadRequestException;
import com.apibancario.exception.InternalServerErrorException;
import com.apibancario.exception.ResourceNotFoundException;
import com.apibancario.feign.AccountFeignCard;
import com.apibancario.mapper.TransaccionMapper;
import com.apibancario.model.dto.transaccion.TransactionRequestDto;
import com.apibancario.model.dto.transaccion.TransactionResponseDto;
import com.apibancario.model.entity.Tarjeta;
import com.apibancario.model.entity.Transaccion;
import com.apibancario.repository.TarjetaRepository;
import com.apibancario.repository.TransaccionRepository;
import com.apibancario.service.TransaccionService;
import com.apibancario.util.GeneradorUtil;
import com.apibancario.util.PasswordUtil;
import feign.FeignException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class TransaccionServiceImpl implements TransaccionService {

    private final TransaccionRepository transaccionRepository;
    private final TarjetaRepository tarjetaRepository;
    private final TransaccionMapper transaccionMapper;
    private final AccountFeignCard accountFeignCard;

    @Override
    public List<TransactionResponseDto> listAll() {
        List<Transaccion> transacciones = transaccionRepository.findAll().stream()
                .peek(transaccion -> {
                    transaccion.getTarjeta().setNumeroTarjeta(
                            GeneradorUtil.ocultarNumeroUID(transaccion.getTarjeta().getNumeroTarjeta())
                    );
                })
                .toList();
        return transaccionMapper.toTransactionsResponseDto(transacciones);
    }

    @Override
    public TransactionResponseDto findById(Integer id) {
        if(id==null || id<=0) throw new BadRequestException("El id es incorrecto");
        Transaccion transaccion = transaccionRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("No se encontró la transaccion con id: "+id)
        );
        return transaccionMapper.toTransactionResponseDto(transaccion);
    }

    @Override
    @Transactional
    public TransactionResponseDto save(TransactionRequestDto transactionRequestDto) {

        String tipo = transactionRequestDto.transactionType().name();
        Integer destino = transactionRequestDto.recipientAccountId();

        switch (tipo) {
            case "DEPOSITO", "RETIRO" -> {
                if (destino != null) {
                    throw new BadRequestException("Depósitos y retiros no requieren cuenta de destino");
                }
            }
            case "TRANSFERENCIA", "PAGO" -> {
                if (destino == null) {
                    throw new BadRequestException("Transferencias y pagos requieren cuenta de destino");
                }
            }
        }

        if(transactionRequestDto.recipientAccountId() != null) {
            try {
                accountFeignCard.findById(transactionRequestDto.recipientAccountId());
            } catch (FeignException.NotFound ex) {
                throw new ResourceNotFoundException("No existe la cuenta con el id: "+transactionRequestDto.recipientAccountId());
            }
        }

        Transaccion transaccion = transaccionMapper.toTransaccion(transactionRequestDto);
        // Proceso de agregar la tarjeta
        Tarjeta tarjeta = tarjetaRepository.findByNumeroTarjeta(transactionRequestDto.cardNumber()).orElseThrow(
                () -> new BadRequestException("Número de tarjeta incorrecto")
        );
        if(Objects.equals(tarjeta.getIdCuenta(), transactionRequestDto.recipientAccountId())) {
            throw new BadRequestException("No es posible realizar pagos o transferencia a la misma cuenta, se recomienda elegir la opción de depósito o retiro");
        }
        if(!PasswordUtil.matches(transactionRequestDto.cardPin(), tarjeta.getPinTarjeta())) {
            throw new BadRequestException("PIN de tarjeta incorrecto");
        }
        transaccion.setTarjeta(tarjeta);

        try {
            return transaccionMapper.toTransactionResponseDto( transaccionRepository.save(transaccion) );
        } catch (Exception ex) {
            throw new BadRequestException("Error al crear nueva tarjeta, ingrese los datos correctos");
        }

    }
}
