package org.primefaces.oasis.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.primefaces.oasis.data.Admin;
import org.primefaces.oasis.repository.AdminRepository;

import java.util.List;


/**
 * Utiliza al repositorio (UserRepository.java) la cual interactua con la base de datos
 * la anotacion (Autowired) del spring realiza la inyeccion de dependencias
 * fecha: 4/27/2023
 * Hecho por: Daniel Santiago Gomez Zabala
 */
@Service
public class AdminService {

    private final AdminRepository userRepository;

    /**
     * Constructor para objetos de clase UserService.
     * @param usuarioRepository Repositorio que accede a la base de datos.
     */
    @Autowired
    public AdminService(AdminRepository usuarioRepository){
        this.userRepository = usuarioRepository;
    }

    /**
     * Agrega un nuevo usuario a la base de datos.
     * @param usuario Usuario que se quiere agregar.
     * @return El usuario que se agregó a la base de datos.
     */
    public Admin addUsuario(Admin usuario){
        return userRepository.save(usuario);
    }

    /**
     * Obtiene la configuración dada la propiedad.
     * @param usuarioId Cadena con el nombre del usuario.
     * @return El usuario que tiene el nombre dado.
     */
    public Admin getUsuario (String usuarioId){
        Admin admin;
        try {
            admin = userRepository.findById(usuarioId).get();
        }catch (Exception e){
            admin = null;
        }
        return admin;
    }

    /**
     * Da todos los usuarios que están en la base de datos.
     * @return Lista con los usuarios disponibles.
     */
    public List<Admin> getAllUsuario(){
        return userRepository.findAll();
    }
    
    /**
     * Actualiza un usuario en la base de datos.
     * @param usuario Usuario que se quiere actualizar.
     * @return El nuevo usuario actualizado.
     */
    public Admin updateUsuario(Admin usuario){
        if(userRepository.existsById(usuario.getId())){
            return userRepository.save(usuario);
        }
        return null;
    }

    /**
     * Elimina un usuario de la base de datos.
     * @param usuarioId Nombre del usuario.
     */
    public void deleteUsuario(String usuarioId){
        userRepository.deleteById(usuarioId);
    }

    /**
     * Método que valida que el usuario exista en la base de datos y permite el acceso
     * NOTA: SE DEBE COLOCAR A DONDE SE VA DIRIGIR DESPUES DE QUE SE VALIDE EL ACCESO
     * @return
     */
    public Boolean login(String nombre, String contrasena) {
        Admin usuario = getUsuario(nombre);
        if (usuario == null || !usuario.getPassword().equals(contrasena)) {
            return false;
        } else {
            return true;
        }
    }

}
