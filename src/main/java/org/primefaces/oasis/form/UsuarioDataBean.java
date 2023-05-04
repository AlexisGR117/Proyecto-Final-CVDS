package org.primefaces.oasis.form;


import lombok.*;
import org.primefaces.oasis.data.Usuario;

import org.primefaces.oasis.service.ConsultaService;
import org.primefaces.oasis.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.ApplicationScope;

import javax.faces.bean.ManagedBean;
import java.io.File;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Lombock creara los getter, setters, constructor equals, to String y demas con la opcion de @Data
 */
@Component
@ManagedBean(name = "usuarioDataBean")
@ApplicationScope
@Getter @Setter
public class UsuarioDataBean implements Serializable {
    @Autowired
    UsuarioService usuarioService;
    @Autowired
    ConsultaService consultaService;

    private String nombre;
    private static final  int precio = 50;
    private ArrayList<Usuario> usuariosRegistrados;
    private boolean seleccionado;

    private static final int intervalo = 30;
    private ArrayList<String> horasDeDia;
    private String email;
    private String ciudad;
    private String noIdentificacion;
    private String telefono;
    private LocalDate fecha;
    private String hora;
    private File comprobantePago;
    private String razonConsulta;
    private String firma;
    public UsuarioDataBean(){
        fecha = LocalDate.now();
        seleccionado = false;
        usuariosRegistrados = new ArrayList<>();
    }

    @Bean
    public CommandLineRunner usuarioActual() throws Exception{
        return args ->{
            Usuario usuario1 = new Usuario("Jeffer", "jeffer.correo@masil.com","301234058","Bogota","1005679","1102891630036719");
            LocalDate fechaGenerica = LocalDate.now();
            //usuario1.anadirConsulta(new Consulta("consulta", fechaGenerica, "1", "3", "2023", "23:00", usuario1));
            usuarioService.addUsuario(usuario1);
            System.out.println("Usuario Creado......." + usuarioService.getUsuario(1L));
            usuarioService.getAllUsuarios();
            usuarioService.deleteUsuario(1L);
            restart();
        };
    }

    public void restart(){
        nombre = "";
        email = "";
        ciudad = "";
        noIdentificacion = "";
        seleccionado = false;
        razonConsulta = "Razon generica";
    }

    public void anadirUsuario() {
        usuarioService.addUsuario(new Usuario(nombre,email,telefono,ciudad,noIdentificacion,firma));
    }
    public void anadirConsulta(){

    }
    public void settingHours(){
        consultaService.getConsultasFecha(fecha);
    }


}
