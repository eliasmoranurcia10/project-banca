package com.apibancario.project_banca.service.impl;

import com.apibancario.project_banca.exception.BadRequestException;
import com.apibancario.project_banca.exception.InternalServerErrorException;
import com.apibancario.project_banca.exception.ResourceNotFoundException;
import com.apibancario.project_banca.mapper.PrestamoMapper;
import com.apibancario.project_banca.model.dto.prestamo.LoanRequestDto;
import com.apibancario.project_banca.model.dto.prestamo.LoanResponseDto;
import com.apibancario.project_banca.model.dto.prestamo.StatusLoanRequestDto;
import com.apibancario.project_banca.model.entity.Cliente;
import com.apibancario.project_banca.model.entity.Prestamo;
import com.apibancario.project_banca.repository.ClienteRepository;
import com.apibancario.project_banca.repository.PrestamoRepository;
import com.apibancario.project_banca.service.PrestamoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PrestamoServiceImpl implements PrestamoService {

    private final PrestamoRepository prestamoRepository;
    private final ClienteRepository clienteRepository;
    private final PrestamoMapper prestamoMapper;

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

        Prestamo prestamo = prestamoMapper.toPrestamo(loanRequestDto);
        Cliente cliente = clienteRepository.findByDni(loanRequestDto.dni()).orElseThrow(
                () -> new ResourceNotFoundException("No se encontró el dni del cliente")
        );
        prestamo.setCliente(cliente);

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
