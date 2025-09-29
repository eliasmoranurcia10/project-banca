package com.apibancario.repository;

import com.apibancario.model.entity.Tarjeta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TarjetaRepository extends JpaRepository<Tarjeta, Integer> {
    Optional<Tarjeta> findByNumeroTarjeta(String numeroTarjeta);
}
