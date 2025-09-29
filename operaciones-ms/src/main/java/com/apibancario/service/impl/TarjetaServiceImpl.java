package com.apibancario.service.impl;

import com.apibancario.exception.BadRequestException;
import com.apibancario.exception.InternalServerErrorException;
import com.apibancario.exception.ResourceNotFoundException;
import com.apibancario.feign.AccountFeignCard;
import com.apibancario.mapper.TarjetaMapper;
import com.apibancario.model.dto.tarjeta.CardPinRequestDto;
import com.apibancario.model.dto.tarjeta.CardRequestDto;
import com.apibancario.model.dto.tarjeta.CardResponseDto;
import com.apibancario.model.entity.Tarjeta;
import com.apibancario.repository.TarjetaRepository;
import com.apibancario.service.TarjetaService;
import com.apibancario.util.GeneradorUtil;
import com.apibancario.util.PasswordUtil;
import feign.FeignException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TarjetaServiceImpl implements TarjetaService {

    private final TarjetaRepository tarjetaRepository;
    private final TarjetaMapper tarjetaMapper;
    private final AccountFeignCard accountFeignCard;

    @Override
    public List<CardResponseDto> listAll() {
        List<Tarjeta> tarjetas = tarjetaRepository.findAll().stream()
                .peek(tarjeta -> {
                    tarjeta.setNumeroTarjeta(GeneradorUtil.ocultarNumeroUID(tarjeta.getNumeroTarjeta()));
                })
                .toList();
        return tarjetaMapper.toCardsResponseDto(tarjetas);
    }

    @Override
    public CardResponseDto findById(Integer id) {
        if(id==null || id<=0) throw new BadRequestException("El id es incorrecto");
        Tarjeta tarjeta = tarjetaRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("No se encontró la tarjeta con id: "+id)
        );
        return tarjetaMapper.toCardResponseDto(tarjeta);
    }

    @Override
    @Transactional
    public CardResponseDto save(CardRequestDto cardRequestDto) {
        try {
            accountFeignCard.findById(cardRequestDto.accountId());
        } catch (FeignException.NotFound ex) {
            throw new ResourceNotFoundException("No existe la cuenta con el id: "+cardRequestDto.accountId());
        }

        Tarjeta tarjeta = tarjetaMapper.toTarjeta(cardRequestDto);

        String numeroTarjetaAleatorio;
        int reintentos=3;
        do {
            numeroTarjetaAleatorio = GeneradorUtil.generarNumeroAleatorio(16);
            reintentos--;
        } while (tarjetaRepository.findByNumeroTarjeta(numeroTarjetaAleatorio).isPresent() && reintentos>0);
        if(reintentos==0) throw new InternalServerErrorException("No fue posible generar un número de tarjeta único");

        tarjeta.setNumeroTarjeta(numeroTarjetaAleatorio);
        tarjeta.setFechaVencimiento(GeneradorUtil.generarFechaVencimientoTarjeta());
        tarjeta.setPinTarjeta(PasswordUtil.hashPassword(tarjeta.getPinTarjeta()));
        tarjeta.setCvvTarjeta(PasswordUtil.hashPassword(GeneradorUtil.generarNumeroAleatorio(3)));

        try{
            return tarjetaMapper.toCardResponseDto(tarjetaRepository.save(tarjeta));
        } catch (Exception ex) {
            throw new BadRequestException("Error al crear nueva tarjeta, ingrese los datos correctos");
        }
    }

    @Override
    @Transactional
    public CardResponseDto updatePinTarjeta(Integer id, CardPinRequestDto cardPinRequestDto) {
        if(id==null || id<=0) throw new BadRequestException("El id es incorrecto");

        Tarjeta tarjeta = tarjetaRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("No se encontró la tarjeta con id: "+id)
        );
        if(!PasswordUtil.matches(cardPinRequestDto.oldPin(), tarjeta.getPinTarjeta())) {
            throw new BadRequestException("Credenciales inválidas");
        }
        tarjeta.setPinTarjeta( PasswordUtil.hashPassword(cardPinRequestDto.newPin()) );

        try {
            return tarjetaMapper.toCardResponseDto(tarjetaRepository.save(tarjeta));
        } catch (Exception ex) {
            throw new InternalServerErrorException("Error al actualizar el pin de la tarjeta");
        }
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        if(id==null || id<=0) throw new BadRequestException("El id es incorrecto");

        Tarjeta tarjeta = tarjetaRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("No se encontró la tarjeta con id: "+id)
        );

        try {
            tarjetaRepository.delete(tarjeta);
        } catch (Exception exception) {
            throw new InternalServerErrorException("Error inesperado al eliminar la tarjeta");
        }
    }
}
