package org.primefaces.oasis.repository;

import org.primefaces.oasis.data.Consulta;
import org.primefaces.oasis.data.ConsultaId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ConsultaRepository extends JpaRepository<Consulta, Long>{
    boolean existsById(ConsultaId consultaId);
    Consulta findById(ConsultaId consultaId);
}
