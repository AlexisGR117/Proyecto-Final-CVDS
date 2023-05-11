package org.primefaces.oasis.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.primefaces.oasis.data.Usuario;
import org.primefaces.oasis.repository.UsuarioRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioSTest{
    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setUsuarioId(1L);
        usuario.setNombre("Juan Perez");
        usuario.setEmail("juan.perez@example.com");
    }

    @Test
    void testAddUsuario() {
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        Usuario usuarioGuardado = usuarioService.addUsuario(usuario);

        verify(usuarioRepository, times(1)).save(usuario);
        assertEquals(usuario, usuarioGuardado);
    }

    @Test
    void testGetUsuario() {
        when(usuarioRepository.findById(anyLong())).thenReturn(Optional.of(usuario));

        Usuario usuarioEncontrado = usuarioService.getUsuario(1L);

        verify(usuarioRepository, times(1)).findById(1L);
        assertEquals(usuario, usuarioEncontrado);
    }

    @Test
    void testGetAllUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        usuarios.add(usuario);

        when(usuarioRepository.findAll()).thenReturn(usuarios);

        List<Usuario> usuariosEncontrados = usuarioService.getAllUsuarios();

        verify(usuarioRepository, times(1)).findAll();
        assertEquals(usuarios, usuariosEncontrados);
    }

    @Test
    void testUpdateUsuario() {
        when(usuarioRepository.existsById(anyLong())).thenReturn(true);
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        Usuario usuarioActualizado = usuarioService.updateUsuario(usuario);

        verify(usuarioRepository, times(1)).existsById(1L);
        verify(usuarioRepository, times(1)).save(usuario);
        assertEquals(usuario, usuarioActualizado);
    }

    @Test
    void testDeleteUsuario() {
        doNothing().when(usuarioRepository).deleteById(anyLong());

        usuarioService.deleteUsuario(1L);

        verify(usuarioRepository, times(1)).deleteById(1L);
    }
}