package com.apibancario.project_banca.mapper;

import com.apibancario.project_banca.model.dto.pagoprestamo.LoanPayRequestDto;
import com.apibancario.project_banca.model.dto.pagoprestamo.LoanPayResponseDto;
import com.apibancario.project_banca.model.entity.PagoPrestamo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Mapper(componentModel = "spring", uses = {PrestamoMapper.class})
public interface PagoPrestamoMapper {

    @Mapping(target = "idPago", ignore = true)
    @Mapping(target = "montoPago", source = "paymentAmount")
    @Mapping(target = "fechaPago", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "prestamo", ignore = true)
    PagoPrestamo toPagoPrestamo(LoanPayRequestDto loanPayRequestDto);
    List<PagoPrestamo> toPagosPrestamo(List<LoanPayRequestDto> loanPaysRequestDto);

    @Mapping(target = "payId", source = "idPago")
    @Mapping(target = "paymentAmount", source = "montoPago")
    @Mapping(target = "paymentDate", source = "fechaPago", qualifiedByName = "fechaPagoToPaymentDate")
    @Mapping(target = "loanResponseDto", source = "prestamo")
    LoanPayResponseDto toLoanPayResponseDto(PagoPrestamo pagoPrestamo);
    List<LoanPayResponseDto> toLoansPayResponseDto(List<PagoPrestamo> pagosPrestamo);

    @Named("fechaPagoToPaymentDate")
    default String fechaPagoToPaymentDate(LocalDateTime fechaPago) {
        return fechaPago==null ? null : fechaPago.format(
                DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")
        );
    }
}
