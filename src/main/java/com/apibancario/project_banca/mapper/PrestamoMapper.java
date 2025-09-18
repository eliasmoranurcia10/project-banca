package com.apibancario.project_banca.mapper;

import com.apibancario.project_banca.model.dto.prestamo.LoanRequestDto;
import com.apibancario.project_banca.model.dto.prestamo.LoanResponseDto;
import com.apibancario.project_banca.model.dto.prestamo.StatusLoanRequestDto;
import com.apibancario.project_banca.model.entity.Prestamo;
import com.apibancario.project_banca.model.enums.EstadoPrestamo;
import com.apibancario.project_banca.model.enums.TipoCuenta;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ClienteMapper.class})
public interface PrestamoMapper {

    @Mapping(target = "idPrestamo", ignore = true)
    @Mapping(target = "montoTotal", source = "totalAmount")
    @Mapping(target = "tasaInteres", source = "interestRate")
    @Mapping(target = "plazoMeses", source = "monthsOfDeadline")
    @Mapping(target = "estado", expression = "java(loanRequestDto.status().name())")
    @Mapping(target = "cliente", ignore = true)
    @Mapping(target = "pagosPrestamo", ignore = true)
    Prestamo toPrestamo(LoanRequestDto loanRequestDto);
    List<Prestamo> toPrestamos(List<LoanRequestDto> loansRequestDto);

    @Mapping(target = "idPrestamo", ignore = true)
    @Mapping(target = "montoTotal", ignore = true)
    @Mapping(target = "tasaInteres", ignore = true)
    @Mapping(target = "plazoMeses", ignore = true)
    @Mapping(target = "pagosPrestamo", ignore = true)
    @Mapping(target = "estado", expression = "java(statusLoanRequestDto.status().name())")
    @Mapping(target = "cliente", ignore = true)
    void updateEstadoPrestamoFromDto(StatusLoanRequestDto statusLoanRequestDto, @MappingTarget Prestamo prestamo);

    @Mapping(target = "loanId", source = "idPrestamo")
    @Mapping(target = "totalAmount", source = "montoTotal")
    @Mapping(target = "interestRate", source = "tasaInteres")
    @Mapping(target = "monthsOfDeadline", source = "plazoMeses")
    @Mapping(target = "clientResponseDto", source = "cliente")
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
