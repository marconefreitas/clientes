package io.github.marconefreitas.model.repository;

import io.github.marconefreitas.model.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
}
