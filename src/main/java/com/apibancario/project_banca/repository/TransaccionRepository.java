package com.apibancario.project_banca.repository;

import com.apibancario.project_banca.model.entity.Transaccion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransaccionRepository extends JpaRepository<Transaccion, Integer> {
}
