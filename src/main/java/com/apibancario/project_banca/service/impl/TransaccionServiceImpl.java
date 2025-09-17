package com.apibancario.project_banca.service.impl;

import com.apibancario.project_banca.exception.BadRequestException;
import com.apibancario.project_banca.exception.InternalServerErrorException;
import com.apibancario.project_banca.exception.ResourceNotFoundException;
import com.apibancario.project_banca.mapper.TransaccionMapper;
import com.apibancario.project_banca.model.dto.transaccion.TransactionRequestDto;
import com.apibancario.project_banca.model.dto.transaccion.TransactionResponseDto;
import com.apibancario.project_banca.model.entity.Cuenta;
import com.apibancario.project_banca.model.entity.Tarjeta;
import com.apibancario.project_banca.model.entity.Transaccion;
import com.apibancario.project_banca.repository.CuentaRepository;
import com.apibancario.project_banca.repository.TarjetaRepository;
import com.apibancario.project_banca.repository.TransaccionRepository;
import com.apibancario.project_banca.service.TransaccionService;
import com.apibancario.project_banca.util.GeneradorUtil;
import com.apibancario.project_banca.util.PasswordUtil;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TransaccionServiceImpl implements TransaccionService {

    private final TransaccionRepository transaccionRepository;
    private final TarjetaRepository tarjetaRepository;
    private final CuentaRepository cuentaRepository;
    private final TransaccionMapper transaccionMapper;

    @Override
    public List<TransactionResponseDto> listAll() {
        List<Transaccion> transacciones = transaccionRepository.findAll().stream()
                .peek(transaccion -> {
                    transaccion.getTarjeta().setNumeroTarjeta(
                            GeneradorUtil.ocultarNumeroUID(transaccion.getTarjeta().getNumeroTarjeta())
                    );

                    transaccion.getTarjeta().getCuenta().setNumeroCuenta(
                            GeneradorUtil.ocultarNumeroUID(transaccion.getTarjeta().getCuenta().getNumeroCuenta())
                    );

                    if(transaccion.getCuentaDestino() != null){
                        transaccion.getCuentaDestino().setNumeroCuenta(
                                GeneradorUtil.ocultarNumeroUID(transaccion.getCuentaDestino().getNumeroCuenta())
                        );
                    }
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

        Transaccion transaccion = transaccionMapper.toTransaccion(transactionRequestDto);

        Tarjeta tarjeta = tarjetaRepository.findByNumeroTarjeta(transactionRequestDto.cardNumber()).orElseThrow(
                () -> new BadRequestException("Número de tarjeta incorrecto")
        );
        if(!PasswordUtil.matches(transactionRequestDto.cardPin(), tarjeta.getPinTarjeta())) {
            throw new BadRequestException("PIN de tarjeta incorrecto");
        }
        transaccion.setTarjeta(tarjeta);


        if(transaccion.getTipoTransaccion().equals("TRANSFERENCIA") || transaccion.getTipoTransaccion().equals("PAGO")) {

            String numeroCuentaDestino = transactionRequestDto.RecipientAccountNumber();
            Cuenta cuentaDestino = cuentaRepository.findByNumeroCuenta(numeroCuentaDestino).orElseThrow(
                    () -> new BadRequestException("Número de cuenta de destino incorrecto")
            );
            transaccion.setCuentaDestino(cuentaDestino);
        }

        Cuenta cuentaTarjeta = transaccion.getTarjeta().getCuenta();
        switch (transaccion.getTipoTransaccion()) {
            case "DEPOSITO" -> depositarMontoDeCuenta(cuentaTarjeta, transaccion);
            case "RETIRO" -> retirarMontoDeCuenta(cuentaTarjeta, transaccion);
            case "TRANSFERENCIA","PAGO" -> {
                retirarMontoDeCuenta(cuentaTarjeta, transaccion);
                depositarMontoDeCuenta(transaccion.getCuentaDestino(), transaccion);
            }
        }

        try {
            return transaccionMapper.toTransactionResponseDto( transaccionRepository.save(transaccion) );
        } catch (Exception ex) {
            throw new BadRequestException("Error al crear nueva tarjeta, ingrese los datos correctos");
        }

    }

    private void depositarMontoDeCuenta(Cuenta cuentaDeposito, Transaccion transaccion) {
        cuentaDeposito.setSaldo( cuentaDeposito.getSaldo().add(transaccion.getMonto()));
        try {
            cuentaRepository.save(cuentaDeposito);
        } catch (Exception ex) {
            throw new InternalServerErrorException("Error en depósito");
        }
    }

    private void retirarMontoDeCuenta(Cuenta cuentaRetiro, Transaccion transaccion) {
        if (cuentaRetiro.getSaldo().compareTo(transaccion.getMonto()) < 0) {
            throw new BadRequestException("Fondos insuficientes");
        }
        cuentaRetiro.setSaldo( cuentaRetiro.getSaldo().subtract( transaccion.getMonto() ) );
        try {
            cuentaRepository.save(cuentaRetiro);
        } catch (Exception ex) {
            throw new InternalServerErrorException("Error en Retiro");
        }

    }
}
