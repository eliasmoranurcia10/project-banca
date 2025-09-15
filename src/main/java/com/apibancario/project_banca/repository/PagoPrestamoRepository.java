package com.apibancario.project_banca.repository;

import com.apibancario.project_banca.model.entity.PagoPrestamo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PagoPrestamoRepository extends JpaRepository<PagoPrestamo, Integer> {
}
