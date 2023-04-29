package org.primefaces.oasis.form;


import lombok.Data;
import org.primefaces.oasis.data.Usuario;
import org.primefaces.oasis.repository.UsuarioRepository;
import org.primefaces.oasis.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.ApplicationScope;

import javax.faces.bean.ManagedBean;
import java.io.Serializable;

/**
 * Lombock creara los getter, setters, constructor equals, to String y demas con la opcion de @Data
 */
@Component
@ManagedBean(name = "usuarioDataBean")
@ApplicationScope
@Data
public class UsuarioDataBean implements Serializable {
    @Autowired
    UsuarioService usuarioService;
    private String nombre;
    private String email;
    private String ciudad;
    private String noIdentificacion;

    @Bean
    public CommandLineRunner usuarioActual() throws Exception{
        return args ->{
            usuarioService.addUsuario(new Usuario("Jeffer", "jeffer@mail","301238","Bogota","10238"));
            usuarioService.getAllUsuarios();
            usuarioService.deleteUsuario(1L);
        };
    }
}
