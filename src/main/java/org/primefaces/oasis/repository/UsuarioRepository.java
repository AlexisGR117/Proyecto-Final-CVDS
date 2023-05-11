package org.primefaces.oasis.repository;

import org.primefaces.oasis.data.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Acceso a la base de datos, realiza operaciones de persistencia de la base de datos para la tabla Usuarios.
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
