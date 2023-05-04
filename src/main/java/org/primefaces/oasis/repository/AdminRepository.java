package org.primefaces.oasis.repository;

import org.springframework.stereotype.Repository;
import org.primefaces.oasis.data.Admin;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * Interface que interactua con la base de datos
 * El JpaRepository proviene de Spring Data JPA la cual ofrece la operaciones CRUD que son necesearias para interfactar con la base de datos
 * fecha: 4/27/2023
 * Hecho por: Daniel Santiago Gomez Zabala
 */
@Repository
public interface AdminRepository extends JpaRepository<Admin, Long>{
    boolean existsById(String propiedadId);

    Admin findById(String propiedadId);
}
