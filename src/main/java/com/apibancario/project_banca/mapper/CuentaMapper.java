package com.apibancario.project_banca.mapper;

import com.apibancario.project_banca.model.dto.cuenta.AccountRequestDto;
import com.apibancario.project_banca.model.dto.cuenta.AccountResponseDto;
import com.apibancario.project_banca.model.entity.Cuenta;
import com.apibancario.project_banca.model.enums.TipoCuenta;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ClienteMapper.class})
public interface CuentaMapper {

    @Mapping(target = "idCuenta", ignore = true)
    @Mapping(target = "numeroCuenta", ignore = true) // Número de cuenta será creado desde el servicio.
    @Mapping(target = "tipoCuenta", expression = "java(accountRequestDto.accountType().name())") // Enum -> String
    @Mapping(target = "claveAcceso", source = "password")
    @Mapping(target = "cliente", ignore = true)
    @Mapping(target = "tarjetas", ignore = true)
    @Mapping(target = "transferenciasRecibidas", ignore = true)
    Cuenta toCuenta(AccountRequestDto accountRequestDto);

    @Mapping(target = "accountId", source = "idCuenta")
    @Mapping(target = "accountNumber", source = "numeroCuenta")
    @Mapping(target = "accountType", source = "tipoCuenta", qualifiedByName = "tipoCuentaToAccountType") // string -> Enum
    @Mapping(target = "clientResponseDto", source = "cliente")
    AccountResponseDto toAccountResponseDto(Cuenta cuenta);
    List<AccountResponseDto> toAccountsResponseDto(List<Cuenta> cuentas);

    @Named("tipoCuentaToAccountType")
    default TipoCuenta tipoCuentaToAccountType(String tipoCuenta) {
        return tipoCuenta == null ? null : switch (tipoCuenta.toUpperCase()) {
            case "AHORRO" -> TipoCuenta.AHORRO;
            case "CORRIENTE" -> TipoCuenta.CORRIENTE;
            default -> null;
        };
    }
}
