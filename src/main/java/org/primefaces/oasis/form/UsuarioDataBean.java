package org.primefaces.oasis.form;


import lombok.*;
import org.primefaces.PrimeFaces;
import org.primefaces.oasis.data.Consulta;
import org.primefaces.oasis.data.ConsultaId;
import org.primefaces.oasis.data.Usuario;

import org.primefaces.oasis.service.ConsultaService;
import org.primefaces.oasis.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.ApplicationScope;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import java.io.File;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

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
    private String email;
    private String telefono;
    private String ciudad;
    private String noIdentificacion;
    private String razonConsulta;
    private LocalDate fecha;
    private String hora;
    private String firma;
    private boolean seleccionado;


    public UsuarioDataBean(){
        fecha = LocalDate.now();
        seleccionado = false;
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
            //restart();
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

    /**
     * Obtiene todas las horas que no estan programadas para el dia seleccioando por el usuario
     * @return Lista de String con las horas disponibles para el dia seleccionado
     */
    public List<String> getConsultasFecha() {
        return consultaService.getConsultasFecha(fecha);
    }

    /**
     * Establece la hora seleccioanda y envia un mensaje con la confirmacion de la seleccion
     * @param hora La hora que selecciono el usuario
     */
    public void setHora(String hora){
        this.hora = hora;
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Fecha de la consulta seleccionada: ", fechaString()  + " a las " + hora);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    /**
     * Con la fecha seleccionada por el usuario se da la toda la fecha en espano
     * @return String con la fecha de la consulta en espanol
     */
    public String fechaString() {
        return fecha.getDayOfWeek().getDisplayName(TextStyle.FULL, new Locale("es", "ES")) +
               " " + fecha.getDayOfMonth() + " de " +
               fecha.getMonth().getDisplayName(TextStyle.FULL, new Locale("es", "ES")) + " de " +
               fecha.getYear();
    }

    /**
     * Programa la consulta con los datos ingresados por el usuario, si no ha seleccionado la hora
     * se manda un mensaje indicando que no lo ha seleccionado, de lo contrario crea el usuario si no
     * existe y crea la consulta.
     * @return La pagina a la cua se quiere redirigir despues de agendar la consulta.
     */
    public String programarConsulta() {
        FacesMessage message;
        if (hora == null) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Seleccione horario", "No ha seleccionado el horario de la consulta");
            FacesContext.getCurrentInstance().addMessage(null, message);
        } else {
            // Lo siguiente no se si va en services
            // Verificar que no exista el usuario
            // Vamos a tomar que el id sea el noIdentifiacion?
            // Si no existe se crea y se agrega a la base de datos
            Usuario usuarioNuevo = new Usuario(nombre, email, telefono, ciudad, noIdentificacion, firma);
            usuarioService.addUsuario(usuarioNuevo);
            // Si existe se obtiene con getUsuario
            // Ahora se crea la nueva consulta y se agrega
            String[] tiempo = hora.split(":");
            ConsultaId consultaIdNueva = new ConsultaId(fecha, LocalTime.of(Integer.parseInt(tiempo[0]),
                    Integer.parseInt(tiempo[1])));
            Consulta consultaNueva = new Consulta(razonConsulta, consultaIdNueva, usuarioNuevo);
            consultaService.addConsulta(consultaNueva);
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Consulta",
                    " Se ha agendado exitosamente su cita!");
            PrimeFaces.current().dialog().showMessageDynamic(message);
            // Ademas toca agregar el envio del correo con la informacion ingresada
        }
        return null;
    }
}
