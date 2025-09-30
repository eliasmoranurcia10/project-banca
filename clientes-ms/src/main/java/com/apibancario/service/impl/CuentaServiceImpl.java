package com.apibancario.service.impl;

import com.apibancario.exception.BadRequestException;
import com.apibancario.exception.InternalServerErrorException;
import com.apibancario.exception.ResourceNotFoundException;
import com.apibancario.mapper.CuentaMapper;
import com.apibancario.model.dto.cuenta.AccountRequestDto;
import com.apibancario.model.dto.cuenta.AccountResponseDto;
import com.apibancario.model.dto.cuenta.PasswordRequestDto;
import com.apibancario.model.dto.cuenta.UpdateSaldoRequestDto;
import com.apibancario.model.entity.Cliente;
import com.apibancario.model.entity.Cuenta;
import com.apibancario.model.enums.TipoOperacion;
import com.apibancario.repository.ClienteRepository;
import com.apibancario.repository.CuentaRepository;
import com.apibancario.service.CuentaService;
import com.apibancario.util.GeneradorUtil;
import com.apibancario.util.PasswordUtil;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
    public AccountResponseDto findByNumberAccount(String numeroCuenta) {
        if(numeroCuenta==null || numeroCuenta.isBlank() || numeroCuenta.length()!=14) throw new BadRequestException("El numero de cuenta es incorrecto");
        Cuenta cuenta = cuentaRepository.findByNumeroCuenta(numeroCuenta).orElseThrow(
                () -> new ResourceNotFoundException("No se encontró la cuenta con el número: "+ numeroCuenta)
        );
        return cuentaMapper.toAccountResponseDto(cuenta);
    }


    @Override
    @Transactional
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
        } catch (Exception ex) {
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
    @Transactional
    public AccountResponseDto updateSaldo(Integer id, UpdateSaldoRequestDto updateSaldoRequestDto){
        TipoOperacion tipoOperacion = updateSaldoRequestDto.tipoOperacion();
        BigDecimal monto = updateSaldoRequestDto.amount();

        if(id==null || id<=0) throw new BadRequestException("El id es incorrecto");
        if (monto == null || monto.compareTo(BigDecimal.ZERO) <= 0) throw new BadRequestException("El monto debe ser mayor a 0");
        Cuenta cuenta = cuentaRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("No se encontró la cuenta con id: "+id)
        );

        switch (tipoOperacion) {
            case DEPOSITO -> cuenta.setSaldo(cuenta.getSaldo().add(monto));
            case RETIRO -> {
                if (monto.compareTo(cuenta.getSaldo())>0) throw new BadRequestException("Saldo Insuficiente");
                cuenta.setSaldo(cuenta.getSaldo().subtract(monto));
            }
            default -> throw new BadRequestException("Tipo de Operación no soportado");
        }
        return cuentaMapper.toAccountResponseDto(cuentaRepository.save(cuenta));
    }

    @Override
    @Transactional
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
