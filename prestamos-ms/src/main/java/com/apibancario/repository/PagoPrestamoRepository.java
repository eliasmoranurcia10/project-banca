package com.apibancario.repository;

import com.apibancario.project_banca.model.entity.PagoPrestamo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PagoPrestamoRepository extends JpaRepository<PagoPrestamo, Integer> {
}
