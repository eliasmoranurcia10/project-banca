package com.apibancario.project_banca.repository;

import com.apibancario.project_banca.model.entity.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrestamoRepository extends JpaRepository<Prestamo, Integer> {
}
