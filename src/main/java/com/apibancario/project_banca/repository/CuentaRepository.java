package com.apibancario.project_banca.repository;

import com.apibancario.project_banca.model.entity.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CuentaRepository extends JpaRepository<Cuenta, Integer> {
}
