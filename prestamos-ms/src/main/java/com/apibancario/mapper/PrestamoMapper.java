package com.apibancario.mapper;

import com.apibancario.model.dto.prestamo.LoanRequestDto;
import com.apibancario.model.dto.prestamo.LoanResponseDto;
import com.apibancario.model.dto.prestamo.StatusLoanRequestDto;
import com.apibancario.model.entity.Prestamo;
import com.apibancario.model.enums.EstadoPrestamo;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PrestamoMapper {


    @Mapping(target = "idPrestamo", ignore = true)
    @Mapping(target = "montoTotal", source = "totalAmount")
    @Mapping(target = "tasaInteres", source = "interestRate")
    @Mapping(target = "plazoMeses", source = "monthsOfDeadline")
    @Mapping(target = "cuotaMensual", ignore = true)
    @Mapping(target = "estado", expression = "java(loanRequestDto.status().name())")
    @Mapping(target = "idCliente", source = "clientId")
    @Mapping(target = "pagosPrestamo", ignore = true)
    Prestamo toPrestamo(LoanRequestDto loanRequestDto);


    @Mapping(target = "idPrestamo", ignore = true)
    @Mapping(target = "montoTotal", ignore = true)
    @Mapping(target = "tasaInteres", ignore = true)
    @Mapping(target = "plazoMeses", ignore = true)
    @Mapping(target = "cuotaMensual", ignore = true)
    @Mapping(target = "pagosPrestamo", ignore = true)
    @Mapping(target = "estado", expression = "java(statusLoanRequestDto.status().name())")
    @Mapping(target = "idCliente", ignore = true)
    void updateEstadoPrestamoFromDto(StatusLoanRequestDto statusLoanRequestDto, @MappingTarget Prestamo prestamo);


    @Mapping(target = "loanId", source = "idPrestamo")
    @Mapping(target = "totalAmount", source = "montoTotal")
    @Mapping(target = "interestRate", source = "tasaInteres")
    @Mapping(target = "monthsOfDeadline", source = "plazoMeses")
    @Mapping(target = "monthlyFee", source = "cuotaMensual")
    @Mapping(target = "clientId", source = "idCliente")
    @Mapping(target = "status", source = "estado", qualifiedByName ="estadoToStatus" )
    LoanResponseDto toLoanResponseDto(Prestamo prestamo);
    List<LoanResponseDto> toLoansResponseDto(List<Prestamo> prestamos);

    @Named("estadoToStatus")
    default EstadoPrestamo estadoToStatus(String estado) {
        return estado == null ? null : switch (estado.toUpperCase()) {
            case "SOLICITADO" -> EstadoPrestamo.SOLICITADO;
            case "APROBADO" -> EstadoPrestamo.APROBADO;
            case "MORA" -> EstadoPrestamo.MORA;
            case "LIQUIDADO" -> EstadoPrestamo.LIQUIDADO;
            default -> null;
        };
    }
}
