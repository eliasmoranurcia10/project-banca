package com.apibancario.service.impl;

import com.apibancario.project_banca.exception.BadRequestException;
import com.apibancario.project_banca.exception.InternalServerErrorException;
import com.apibancario.project_banca.exception.ResourceNotFoundException;
import com.apibancario.project_banca.mapper.PagoPrestamoMapper;
import com.apibancario.project_banca.model.dto.pagoprestamo.LoanPayRequestDto;
import com.apibancario.project_banca.model.dto.pagoprestamo.LoanPayResponseDto;
import com.apibancario.project_banca.model.entity.PagoPrestamo;
import com.apibancario.project_banca.model.entity.Prestamo;
import com.apibancario.project_banca.repository.PagoPrestamoRepository;
import com.apibancario.project_banca.repository.PrestamoRepository;
import com.apibancario.project_banca.service.PagoPrestamoService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PagoPrestamoServiceImpl implements PagoPrestamoService {

    private final PagoPrestamoRepository pagoPrestamoRepository;
    private final PagoPrestamoMapper pagoPrestamoMapper;
    private final PrestamoRepository prestamoRepository;

    @Override
    public List<LoanPayResponseDto> listAll() {
        List<PagoPrestamo> pagosPrestamo = pagoPrestamoRepository.findAll();
        return pagoPrestamoMapper.toLoansPayResponseDto(pagosPrestamo);
    }

    @Override
    public LoanPayResponseDto findById(Integer id) {
        if(id==null || id<=0) throw new BadRequestException("El id es incorrecto");

        PagoPrestamo pagoPrestamo = pagoPrestamoRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("No se encontró el prestamo con id: "+id)
        );
        return pagoPrestamoMapper.toLoanPayResponseDto(pagoPrestamo);
    }

    @Override
    @Transactional
    public LoanPayResponseDto save(LoanPayRequestDto loanPayRequestDto) {
        Prestamo prestamo = prestamoRepository.findById(loanPayRequestDto.loanId()).orElseThrow(
                () -> new ResourceNotFoundException("Préstamo no encontrado")
        );

        if(prestamo.getEstado().equals("SOLICITADO") || prestamo.getEstado().equals("LIQUIDADO") ) {
            throw new BadRequestException("No puede realizar pagos al préstamo por encontrarse: "+ prestamo.getEstado());
        }

        if(loanPayRequestDto.paymentAmount().compareTo(prestamo.getCuotaMensual()) != 0) {
            throw new BadRequestException("Cuota incorrecta. Su cuota es de:" + prestamo.getCuotaMensual() );
        }

        PagoPrestamo pagoPrestamo = pagoPrestamoMapper.toPagoPrestamo(loanPayRequestDto);
        pagoPrestamo.setPrestamo(prestamo);
        try {
            return pagoPrestamoMapper.toLoanPayResponseDto(pagoPrestamoRepository.save( pagoPrestamo ));
        } catch (Exception ex) {
            throw new InternalServerErrorException("Error al guardar pago de préstamo");
        }
    }
}
