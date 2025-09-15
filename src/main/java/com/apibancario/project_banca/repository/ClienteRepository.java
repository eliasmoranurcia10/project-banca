package com.apibancario.project_banca.repository;

import com.apibancario.project_banca.model.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
}
