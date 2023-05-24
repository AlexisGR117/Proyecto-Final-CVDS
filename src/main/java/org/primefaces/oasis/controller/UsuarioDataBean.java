package org.primefaces.oasis.controller;


import lombok.Getter;
import lombok.Setter;
import org.primefaces.PrimeFaces;
import org.primefaces.oasis.data.Consulta;
import org.primefaces.oasis.data.ConsultaId;
import org.primefaces.oasis.data.Usuario;
import org.primefaces.oasis.exceptions.ConsultasException;
import org.primefaces.oasis.service.ConsultaService;
import org.primefaces.oasis.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * Bean que maneja la interacciones del usuario con la página del formulario para programar la consulta.
 */
@Component
@ManagedBean(name = "usuarioDataBean")
@ViewScoped
@Getter
@Setter
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
    private LocalDate fechaMinima;
    private LocalDate fechaMaxima;
    private int precio;
    private List<Integer> diasDeshabilitados;

    /**
     * Constructor para objetos de clase UsuarioDataBean.
     */
    public UsuarioDataBean() {
        fecha = LocalDate.now().plusDays(1);
        seleccionado = false;
        fechaMinima = LocalDate.now().plusDays(1);
        fechaMaxima = LocalDate.now().plusDays(ConsultaService.DIAS_MAXIMO);
        diasDeshabilitados = ConsultaService.DIAS_DESHABILITADOS;
        precio = ConsultaService.PRECIO;
    }

    /**
     * Crea un usuario nuevo con los datos ingresados y lo guarda en la base de datos.
     *
     * @return El nuevo usuario que se ha creado.
     */
    public Usuario agregarUsuario() {
        Usuario nuevoUsuario = new Usuario(nombre, email, telefono, ciudad, noIdentificacion, firma);
        usuarioService.addUsuario(nuevoUsuario);
        return nuevoUsuario;
    }

    /**
     * Crea una consulta nueva con los datos ingresados y la guarda en la base de datos.
     *
     * @param usuario Usuario que programo la consulta.
     */
    public void agregarConsulta(Usuario usuario) throws ConsultasException {
        try {
            String[] tiempo = hora.split(":");
            ConsultaId consultaIdNueva = new ConsultaId(fecha, LocalTime.of(Integer.parseInt(tiempo[0]),
                    Integer.parseInt(tiempo[1])));
            Consulta consultaNueva = new Consulta(razonConsulta, consultaIdNueva, usuario);
            consultaService.addConsulta(consultaNueva);
            consultaService.enviarCorreo(usuario,consultaNueva);
        } catch (NullPointerException e) {
            throw new ConsultasException(ConsultasException.CONSULTA_SIN_HORA);
        }
    }

    /**
     * Obtiene todas las horas que no se encuentran programadas para la fecha seleccionada por el usuario.
     *
     * @return Lista de String con las horas disponibles para el dia seleccionado.
     */
    public List<String> getConsultasFecha() {
        try {
            return consultaService.getConsultasFecha(fecha);
        } catch (ConsultasException e) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Error!: ", e.getMessage());
            PrimeFaces.current().dialog().showMessageDynamic(message);
            return Collections.emptyList();
        }
    }

    /**
     * Establece la hora seleccionada y manda un mensaje con esta.
     *
     * @param hora La hora que ha elegido el usuario.
     */
    public void setHora(String hora) {
        this.hora = hora;
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Fecha de la consulta seleccionada: ", fechaString() + " a las " + hora);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    /**
     * Con la fecha seleccionada por el usuario se da toda la fecha en espanol.
     *
     * @return String con la fecha de la consulta en espanol.
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
     *
     * @return El sitio web a la cual se quiere redirigir al agendar la consulta.
     */
    public String programarConsulta() {
        FacesMessage message;
        try {
            Usuario usuarioNuevo = agregarUsuario();
            agregarConsulta(usuarioNuevo);
        } catch (ConsultasException e) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Seleccione horario", e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, message);
            return null;
        }
        message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Consulta",
                " Se ha agendado exitosamente su cita!");
        PrimeFaces.current().dialog().showMessageDynamic(message);
        return "inicio.xhtml";
    }
}