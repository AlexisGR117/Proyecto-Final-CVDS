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
    public Usuario addUsuario(Usuario usuario){
        return usuarioRepository.save(usuario);
    }
    public Usuario getUsuario(Long userId){
        return usuarioRepository.findById(userId).get();
    }
    public List<Usuario> getAllUsuarios(){
        return usuarioRepository.findAll();
    }

    public Usuario updateUsuario(Usuario usuario){
        if(usuarioRepository.existsById(usuario.getUsuaioId())) {
            return usuarioRepository.save(usuario);
        }
        return null;
    }
    public void deleteUsuario(Long usuarioId){
        usuarioRepository.deleteById(usuarioId);
    }
}
