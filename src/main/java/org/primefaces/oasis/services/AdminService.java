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
    @Autowired
    public AdminService(AdminRepository usuarioRepository){
        this.userRepository = usuarioRepository;
    }
    public Admin addUsuario(Admin usuario){
        return userRepository.save(usuario);
    }
    public Admin getUsuario (String usuarioId){
        return userRepository.findById(usuarioId);
    }
    public List<Admin> getAllUsuario(){
        return userRepository.findAll();
    }
    public Admin updateUsuario(Admin usuario){
        if(userRepository.existsById(usuario.getId())){
            return userRepository.save(usuario);
        }
        return null;
    }

    public void deleteUsuario(Long usuarioId){
        userRepository.deleteById(usuarioId);
    }

}
