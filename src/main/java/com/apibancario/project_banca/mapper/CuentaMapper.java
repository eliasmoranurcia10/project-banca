package com.apibancario.project_banca.mapper;

import com.apibancario.project_banca.model.dto.cuenta.AccountRequestDto;
import com.apibancario.project_banca.model.dto.cuenta.AccountResponseDto;
import com.apibancario.project_banca.model.entity.Cuenta;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CuentaMapper {

    @Mapping(target = "idCuenta", ignore = true)
    @Mapping(target = "numeroCuenta", ignore = true)
    @Mapping(target = "tipoCuenta", expression = "java(accountRequestDto.accountType().name())")
    @Mapping(target = "claveAcceso", source = "password")
    @Mapping(target = "cliente", ignore = true)
    @Mapping(target = "tarjetas", ignore = true)
    @Mapping(target = "transferenciasRecibidas", ignore = true)
    Cuenta toCuenta(AccountRequestDto accountRequestDto);
    List<Cuenta> toCuentas(List<AccountRequestDto> accountsRequestDto);

    @Mapping(target = "accountId", source = "idCuenta")
    @Mapping(target = "accountNumber", source = "")
    @Mapping(target = "accountType", source = "")
    @Mapping(target = "clientResponseDto", source = "")
    AccountResponseDto toAccountResponseDto(Cuenta cuenta);
}
