package com.apibancario.project_banca.mapper;

import com.apibancario.project_banca.model.dto.prestamo.LoanRequestDto;
import com.apibancario.project_banca.model.dto.prestamo.LoanResponseDto;
import com.apibancario.project_banca.model.entity.Prestamo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ClienteMapper.class})
public interface PrestamoMapper {

    @Mapping(target = "idPrestamo", ignore = true)
    @Mapping(target = "montoTotal", source = "totalAmount")
    @Mapping(target = "tasaInteres", source = "interestRate")
    @Mapping(target = "plazoMeses", source = "monthsOfDeadline")
    @Mapping(target = "cliente", ignore = true)
    @Mapping(target = "pagosPrestamo", ignore = true)
    Prestamo toPrestamo(LoanRequestDto loanRequestDto);
    List<Prestamo> toPrestamos(List<LoanRequestDto> loansRequestDto);

    @Mapping(target = "loanId", source = "idPrestamo")
    @Mapping(target = "totalAmount", source = "montoTotal")
    @Mapping(target = "interestRate", source = "tasaInteres")
    @Mapping(target = "monthsOfDeadline", source = "plazoMeses")
    @Mapping(target = "clientResponseDto", source = "cliente")
    LoanResponseDto toLoanResponseDto(Prestamo prestamo);
    List<LoanResponseDto> toLoansResponseDto(List<Prestamo> prestamos);
}
