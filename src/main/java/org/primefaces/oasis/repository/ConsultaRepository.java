package org.primefaces.oasis.repository;

import org.primefaces.oasis.data.Consulta;
import org.primefaces.oasis.data.ConsultaId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Acceso a la base de datos, realiza operaciones de persistencia de la base de datos para la tabla Consultas.
 */
@Repository
public interface ConsultaRepository extends JpaRepository<Consulta, ConsultaId> {
}
