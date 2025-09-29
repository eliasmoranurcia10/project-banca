package com.apibancario.service.impl;

import com.apibancario.exception.BadRequestException;
import com.apibancario.exception.InternalServerErrorException;
import com.apibancario.exception.ResourceNotFoundException;
import com.apibancario.feign.ClientFeignLoan;
import com.apibancario.mapper.PrestamoMapper;
import com.apibancario.model.dto.prestamo.LoanRequestDto;
import com.apibancario.model.dto.prestamo.LoanResponseDto;
import com.apibancario.model.dto.prestamo.StatusLoanRequestDto;
import com.apibancario.model.entity.Prestamo;
import com.apibancario.repository.PrestamoRepository;
import com.apibancario.service.PrestamoService;
import com.apibancario.util.CalculatorUtil;
import feign.FeignException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PrestamoServiceImpl implements PrestamoService {

    private final PrestamoRepository prestamoRepository;
    private final PrestamoMapper prestamoMapper;
    private final ClientFeignLoan clientFeignLoan;

    @Override
    public List<LoanResponseDto> listAll() {
        List<Prestamo> prestamos = prestamoRepository.findAll();
        return prestamoMapper.toLoansResponseDto(prestamos);
    }

    @Override
    public LoanResponseDto findById(Integer id) {
        if(id==null || id<=0) throw new BadRequestException("El id es incorrecto");
        Prestamo prestamo = prestamoRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("No se encuentra un préstamo con el id: "+id)
        );
        return prestamoMapper.toLoanResponseDto(prestamo);
    }

    @Override
    public LoanResponseDto save(LoanRequestDto loanRequestDto) {

        try {
            clientFeignLoan.findById(loanRequestDto.clientId());
        } catch (FeignException.NotFound ex) {
            throw new ResourceNotFoundException("No existe el cliente con el id: "+loanRequestDto.clientId());
        }

        Prestamo prestamo = prestamoMapper.toPrestamo(loanRequestDto);

        prestamo.setCuotaMensual(CalculatorUtil.calcularCuotaMensual(
                prestamo.getTasaInteres(),
                prestamo.getMontoTotal(),
                prestamo.getPlazoMeses()
        ));

        try {
            return prestamoMapper.toLoanResponseDto(prestamoRepository.save(prestamo));
        } catch (Exception exception) {
            throw new BadRequestException("Error al crear nuevo préstamo, ingresar datos correctos");
        }

    }

    @Override
    public LoanResponseDto updateStatusLoan(Integer id, StatusLoanRequestDto statusLoanRequestDto) {
        if(id==null || id<=0) throw new BadRequestException("El id es incorrecto");
        Prestamo prestamo = prestamoRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("No se encontró el préstamo con el id: "+id)
        );
        prestamoMapper.updateEstadoPrestamoFromDto(statusLoanRequestDto, prestamo);
        try{
            return prestamoMapper.toLoanResponseDto(prestamoRepository.save(prestamo));
        } catch (Exception ex) {
            throw new InternalServerErrorException("Error al actualizar el estado del préstamo");
        }
    }
}
