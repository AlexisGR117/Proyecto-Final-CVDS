package org.primefaces.oasis.service;

import org.primefaces.oasis.data.Admin;
import org.primefaces.oasis.exceptions.AdminException;
import org.primefaces.oasis.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Utiliza al repositorio (UserRepository.java) el cual sirve para interactuar con la base de datos-
 * Autowired del spring realiza la inyeccion de dependencias-
 *
 * @author Daniel Santiago Gomez Zabala
 * @version 4/27/2023
 */
@Service
public class AdminService {

    private final AdminRepository adminRepository;

    /**
     * Constructor para objetos de clase AdminService.
     *
     * @param adminRepository Repositorio que accede a la base de datos.
     */
    @Autowired
    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    /**
     * Agrega un nuevo administrador a la base de datos.
     *
     * @param admin Administrador que se quiere agregar.
     * @return El administrador que se agregó a la base de datos.
     */
    public Admin addAdmin(Admin admin) {
        return adminRepository.save(admin);
    }

    /**
     * Obtiene el administrador dada su id.
     *
     * @param adminId Cadena con el nombre del administrador.
     * @return El administrador que tiene el nombre dado.
     */
    public Admin getAdmin(String adminId) {
        Optional<Admin> optionalAdmin = adminRepository.findById(adminId);
        Admin admin = null;
        if (optionalAdmin.isPresent()) admin = optionalAdmin.get();
        return admin;
    }

    /**
     * Da todos los administradores que se encuentran en la base de datos.
     *
     * @return Lista con los administradores disponibles.
     */
    public List<Admin> getAllAdmin() {
        return adminRepository.findAll();
    }

    /**
     * Actualiza un administrador en la base de datos.
     *
     * @param admin administrador que se quiere actualizar.
     * @return El nuevo usuario actualizado.
     */
    public Admin updateAdmin(Admin admin) {
        if (adminRepository.existsById(admin.getNombre())) {
            return adminRepository.save(admin);
        }
        return null;
    }

    /**
     * Elimina un administrador de la base de datos.
     *
     * @param adminId Nombre del administrador.
     */
    public void deleteAdmin(String adminId) {
        adminRepository.deleteById(adminId);
    }

    /**
     * Verifica que el usuario exista en la base de datos y permite el acceso.
     *
     * @throws AdminException INVALID_PASSWORD, si el usuario existe pero la contrasena dada es incorrecta.
     *                        INVALID_NAME, si no existe un administrador con ese nombre de usuario.
     */
    public void login(String nombre, String contrasena) throws AdminException {
        if (nombre.equals("")) throw new AdminException(AdminException.NOMBRE_VACIO);
        if (contrasena.equals("")) throw new AdminException(AdminException.CONTRASENA_VACIA);
        Admin admin = getAdmin(nombre);
        if (admin == null) throw new AdminException(AdminException.NOMBRE_INVALIDO);
        else if (!admin.getContrasena().equals(contrasena)) {
            throw new AdminException(AdminException.CONSTRASENA_INVALIDA);
        }
    }
}