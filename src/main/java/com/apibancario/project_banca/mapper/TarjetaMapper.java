package com.apibancario.project_banca.mapper;

import com.apibancario.project_banca.model.dto.tarjeta.CardRequestDto;
import com.apibancario.project_banca.model.dto.tarjeta.CardResponseDto;
import com.apibancario.project_banca.model.entity.Tarjeta;
import com.apibancario.project_banca.model.enums.TipoTarjeta;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Mapper(componentModel = "spring", uses = {CuentaMapper.class})
public interface TarjetaMapper {

    @Mapping(target = "idTarjeta", ignore = true)
    @Mapping(target = "numeroTarjeta", ignore = true)
    @Mapping(target = "tipoTarjeta", expression = "java(cardRequestDto.cardType().name())")
    @Mapping(target = "fechaVencimiento", ignore = true)
    @Mapping(target = "pinTarjeta", ignore = true)
    @Mapping(target = "cvvTarjeta", ignore = true)
    @Mapping(target = "cuenta", ignore = true)
    @Mapping(target = "transacciones", ignore = true)
    Tarjeta toTarjeta(CardRequestDto cardRequestDto);
    List<Tarjeta> toTarjetas(List<CardRequestDto> cardsRequestDto);

    @Mapping(target = "cardId", source = "idTarjeta")
    @Mapping(target = "cardNumber", source = "numeroTarjeta")
    @Mapping(target = "cardType", source = "tipoTarjeta", qualifiedByName = "tipoTarjetaToCardType")
    @Mapping(target = "expirationDate", source = "fechaVencimiento")
    @Mapping(target = "accountResponseDto", source = "cuenta")
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
