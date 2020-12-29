package io.github.marconefreitas.model.repository;

import io.github.marconefreitas.model.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {


}
