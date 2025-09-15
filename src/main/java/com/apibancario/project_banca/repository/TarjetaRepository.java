package com.apibancario.project_banca.repository;

import com.apibancario.project_banca.model.entity.Tarjeta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TarjetaRepository extends JpaRepository<Tarjeta, Integer> {
}
