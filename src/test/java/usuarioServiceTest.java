import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.primefaces.oasis.data.Usuario;
import org.primefaces.oasis.repository.UsuarioRepository;
import org.primefaces.oasis.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
@RunWith(SpringRunner.class)
@SpringBootTest
public class usuarioServiceTest {
    @Autowired
    private UsuarioService usuarioService;
    @MockBean
    private UsuarioRepository usuarioRepository;

    @Test
    public void deberiaCrearUsuarioNuevo(){
        Usuario user1 = new Usuario("David", "david@mail", "12345123", "Chia", "1234541","12345");
        System.out.println("PRUEBA 1 ..........");
        usuarioService.addUsuario(user1);
        List<Usuario> usuarios = usuarioService.getAllUsuarios();
        assertTrue(usuarios.size() > 0);
    }

    /**
     * Este debe de fallar
     */
    @Test
    public void deberiaEliminarUsuario(){
        usuarioService.deleteUsuario(1L);
        List<Usuario> usuarios = usuarioService.getAllUsuarios();
        assertEquals(3, usuarios.size());
    }

}
