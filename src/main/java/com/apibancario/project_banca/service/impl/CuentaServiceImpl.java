package com.apibancario.project_banca.service.impl;

import com.apibancario.project_banca.exception.BadRequestException;
import com.apibancario.project_banca.exception.InternalServerErrorException;
import com.apibancario.project_banca.exception.ResourceNotFoundException;
import com.apibancario.project_banca.mapper.CuentaMapper;
import com.apibancario.project_banca.model.dto.cuenta.AccountRequestDto;
import com.apibancario.project_banca.model.dto.cuenta.AccountResponseDto;
import com.apibancario.project_banca.model.dto.cuenta.PasswordRequestDto;
import com.apibancario.project_banca.model.entity.Cliente;
import com.apibancario.project_banca.model.entity.Cuenta;
import com.apibancario.project_banca.repository.ClienteRepository;
import com.apibancario.project_banca.repository.CuentaRepository;
import com.apibancario.project_banca.service.CuentaService;
import com.apibancario.project_banca.util.GeneradorUtil;
import com.apibancario.project_banca.util.PasswordUtil;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CuentaServiceImpl implements CuentaService {

    private final CuentaRepository cuentaRepository;
    private final CuentaMapper cuentaMapper;
    private final ClienteRepository clienteRepository;

    @Override
    public List<AccountResponseDto> listAll() {
        List<Cuenta> cuentas = cuentaRepository.findAll().stream()
                .peek(cuenta -> cuenta.setNumeroCuenta( GeneradorUtil.ocultarNumeroUID( cuenta.getNumeroCuenta())))
                .toList();
        return cuentaMapper.toAccountsResponseDto( cuentas );
    }

    @Override
    public AccountResponseDto findById(Integer id) {
        if(id==null || id<=0) throw new BadRequestException("El id es incorrecto");
        Cuenta cuenta = cuentaRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("No se encontró la cuenta con id: "+id)
        );
        return cuentaMapper.toAccountResponseDto(cuenta);
    }

    @Override
    public AccountResponseDto save(AccountRequestDto accountRequestDto) {

        Cuenta cuenta = cuentaMapper.toCuenta(accountRequestDto);

        String numeroCuentaAleatorio;
        int reintentos = 3;
        do{
            numeroCuentaAleatorio = GeneradorUtil.generarNumeroAleatorio(14);
            reintentos--;
        } while (cuentaRepository.findByNumeroCuenta(numeroCuentaAleatorio).isPresent() && reintentos>0 );
        if(reintentos==0) throw new InternalServerErrorException("No fue posible generar un número de cuenta único");

        cuenta.setNumeroCuenta(numeroCuentaAleatorio);
        cuenta.setClaveAcceso(PasswordUtil.hashPassword(cuenta.getClaveAcceso()));
        Cliente cliente = clienteRepository.findById(accountRequestDto.clientId()).orElseThrow(
                () -> new ResourceNotFoundException("No se encontró el id del cliente")
        );
        cuenta.setCliente(cliente);

        try {
            return cuentaMapper.toAccountResponseDto(cuentaRepository.save(cuenta));
        } catch (DataIntegrityViolationException ex) {
            throw new BadRequestException("Error al crear nueva cuenta, ingresar datos correctos");
        }
    }

    @Override
    @Transactional
    public AccountResponseDto updatePassword(Integer id, PasswordRequestDto passwordRequestDto) {
        if(id==null || id<=0) throw new BadRequestException("El id es incorrecto");
        Cuenta cuenta = cuentaRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("No se encontró la cuenta con id: "+id)
        );
        if(!PasswordUtil.matches(passwordRequestDto.oldPassword(), cuenta.getClaveAcceso() )) {
            throw new BadRequestException("Credenciales inválidas");
        }
        cuenta.setClaveAcceso(PasswordUtil.hashPassword(passwordRequestDto.newPassword()));
        try {
            return cuentaMapper.toAccountResponseDto(cuentaRepository.save(cuenta));
        } catch (Exception ex) {
            throw new InternalServerErrorException("Error al actualizar la clave de acceso");
        }
    }

    @Override
    public void delete(Integer id) {
        if(id==null || id<=0) throw new BadRequestException("El id es incorrecto");
        Cuenta cuenta = cuentaRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("No se encontró la cuenta con id: "+id)
        );
        try {
            cuentaRepository.delete(cuenta);
        } catch (Exception exception) {
            throw new InternalServerErrorException("Error inesperado al eliminar la cuenta");
        }
    }
}
