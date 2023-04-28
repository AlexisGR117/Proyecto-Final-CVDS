package org.primefaces.oasis.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.primefaces.oasis.repositories.UserRepository;
import org.primefaces.oasis.model.User;

import java.util.List;

/**
 * Utiliza al repositorio (UserRepository.java) la cual interactua con la base de datos
 * la anotacion (Autowired) del spring realiza la inyeccion de dependencias
 * fecha: 4/27/2023
 * Hecho por: Daniel Santiago Gomez Zabala
 */
@Service
public class UserService {
    private final UserRepository userRepository;
    @Autowired
    public UserService(UserRepository usuarioRepository){
        this.userRepository = usuarioRepository;
    }
    public User addUsuario(User usuario){
        return userRepository.save(usuario);
    }
    public User getUsuario (String usuarioId){
        return userRepository.findById(usuarioId);
    }
    public List<User> getAllUsuario(){
        return userRepository.findAll();
    }
    public User updateUsuario(User usuario){
        if(userRepository.existsById(usuario.getId())){
            return userRepository.save(usuario);
        }
        return null;
    }

    public void deleteUsuario(Long usuarioId){
        userRepository.deleteById(usuarioId);
    }

}
