package org.primefaces.oasis.service;

import org.primefaces.oasis.data.Usuario;
import org.primefaces.oasis.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

/**
 * Clase que se encarga de la lógica de negocio de la aplicacion relacionada con los usuarios.
 */
@Service
public class UsuarioService implements Serializable {
    private final UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * Añade un usuario a la base de datos.
     *
     * @param usuario Usuario que se quiere agregar a la base de datos.
     * @return El usuario que se agregó a la base de datos.
     */
    public Usuario addUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    /**
     * Retorna un usuario dada su id.
     *
     * @param userId Long con el identificador del usuario.
     * @return El usuario que tiene el identificador dado.
     */
    public Usuario getUsuario(Long userId) {
        return usuarioRepository.findById(userId).get();
    }

    /**
     * Obtiene todos los usuarios en la base de datos.
     *
     * @return Todos los usuarios que hay en la base de datos.
     */
    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    /**
     * Actualiza un usuario ya existente en la base de datos.
     *
     * @param usuario El usuario que se quiere actualizar.
     * @return El usuario actualizado.
     */
    public Usuario updateUsuario(Usuario usuario) {
        if (usuarioRepository.existsById(usuario.getUsuarioId())) {
            return usuarioRepository.save(usuario);
        }
        return null;
    }

    /**
     * Elimina un usuario en la base de datos.
     *
     * @param usuarioId Long con el identificador del usuario que se quiere eliminar.
     */
    public void deleteUsuario(Long usuarioId) {
        usuarioRepository.deleteById(usuarioId);
    }
}
