package org.primefaces.oasis.repository;

import org.primefaces.oasis.data.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interfaz que sirve para interactuar con la base de datos
 * El JpaRepository proviene de Spring Data JPA la cual ofrece las operaciones CRUD que son necesarias para
 * interactuar con la base de datos
 *
 * @author Daniel Santiago Gomez Zabala
 * @version 4/27/2023
 */
@Repository
public interface AdminRepository extends JpaRepository<Admin, String> {
}