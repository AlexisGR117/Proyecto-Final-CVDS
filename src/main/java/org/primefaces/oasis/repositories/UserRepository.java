package org.primefaces.oasis.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import org.primefaces.oasis.model.User;


/**
 * Interface que interactua con la base de datos
 * El JpaRepository proviene de Spring Data JPA la cual ofrece la operaciones CRUD que son necesearias para interfactar con la base de datos
 * fecha: 4/27/2023
 * Hecho por: Daniel Santiago Gomez Zabala
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    boolean existsById(String propiedadId);

    User findById(String propiedadId);
}
