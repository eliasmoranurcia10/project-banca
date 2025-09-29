package com.apibancario.mapper;

import com.apibancario.model.dto.tarjeta.CardRequestDto;
import com.apibancario.model.dto.tarjeta.CardResponseDto;
import com.apibancario.model.entity.Tarjeta;
import com.apibancario.model.enums.TipoTarjeta;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TarjetaMapper {

    @Mapping(target = "idTarjeta", ignore = true)
    @Mapping(target = "numeroTarjeta", ignore = true)
    @Mapping(target = "tipoTarjeta", expression = "java(cardRequestDto.cardType().name())")
    @Mapping(target = "fechaVencimiento", ignore = true)
    @Mapping(target = "pinTarjeta", source = "cardPin")
    @Mapping(target = "cvvTarjeta", ignore = true)
    @Mapping(target = "transacciones", ignore = true)
    @Mapping(target = "idCuenta", source = "accountId")
    Tarjeta toTarjeta(CardRequestDto cardRequestDto);

    @Mapping(target = "cardId", source = "idTarjeta")
    @Mapping(target = "cardNumber", source = "numeroTarjeta")
    @Mapping(target = "cardType", source = "tipoTarjeta", qualifiedByName = "tipoTarjetaToCardType")
    @Mapping(target = "expirationDate", source = "fechaVencimiento")
    @Mapping(target = "accountId", source = "idCuenta")
    CardResponseDto toCardResponseDto(Tarjeta tarjeta);
    List<CardResponseDto> toCardsResponseDto(List<Tarjeta> tarjetas);

    @Named("tipoTarjetaToCardType")
    default TipoTarjeta tipoTarjetaToCardType(String tipoTarjeta) {
        return tipoTarjeta==null? null: switch (tipoTarjeta.toUpperCase()) {
            case "DEBITO" -> TipoTarjeta.DEBITO;
            case "CREDITO" -> TipoTarjeta.CREDITO;
            default -> null;
        };
    }
}
