package org.primefaces.oasis.service;

import org.primefaces.oasis.data.Usuario;
import org.primefaces.oasis.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService{
    private final UsuarioRepository usuarioRepository;
    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository){
        this.usuarioRepository=usuarioRepository;
    }

    /**
     * Este metodo se encarga de añadir un susuario a la base de datos
     * @param usuario
     * @return
     */
    public Usuario addUsuario(Usuario usuario){
        return usuarioRepository.save(usuario);
    }

    /**
     * Este metodo me retornara un usuario
     * @param userId
     * @return
     */
    public Usuario getUsuario(Long userId){
        return usuarioRepository.findById(userId).get();
    }

    /**
     * Este metodo se encarga de obtener todos los usuarios en la base de datos
     * @return
     */
    public List<Usuario> getAllUsuarios(){
        return usuarioRepository.findAll();
    }

    /**
     * Este metodo se encarga de actualizar un usuario ya existente en la base de datos
     * @param usuario
     * @return
     */
    public Usuario updateUsuario(Usuario usuario){
        if(usuarioRepository.existsById(usuario.getUsuaioId())) {
            return usuarioRepository.save(usuario);
        }
        return null;
    }

    /**
     * Este metodo se encargca de eliminar un usuario en la base de datos
     * @param usuarioId
     */
    public void deleteUsuario(Long usuarioId){
        usuarioRepository.deleteById(usuarioId);
    }
}
