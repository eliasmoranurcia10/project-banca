package com.apibancario.project_banca.repository;

import com.apibancario.project_banca.model.entity.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CuentaRepository extends JpaRepository<Cuenta, Integer> {
}
