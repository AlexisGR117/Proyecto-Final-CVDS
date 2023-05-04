import org.junit.Test;
import org.primefaces.oasis.repository.ConsultaRepository;
import org.primefaces.oasis.repository.UsuarioRepository;
import org.primefaces.oasis.service.ConsultaService;
import org.primefaces.oasis.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class consultaServiceTest {
    @Autowired
    private ConsultaService consultaService;
    @MockBean
    private ConsultaRepository consultaRepository;

    @Test
    public void deberiaGenerarHorasDelDia(){
        ArrayList<String> ans = consultaService.hourSetter(15,7,30);
        assertEquals(10, ans.size());
    }
}
