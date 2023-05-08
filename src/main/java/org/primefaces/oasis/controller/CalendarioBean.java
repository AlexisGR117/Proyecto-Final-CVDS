package org.primefaces.oasis.controller;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;

import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.file.UploadedFile;
import org.primefaces.oasis.service.ConsultaService;
import org.primefaces.oasis.service.EstadoConsulta;
import org.primefaces.oasis.service.UsuarioService;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.ScheduleModel;
import org.primefaces.oasis.data.Consulta;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;

/**
 * Bean que maneja la interacciones del administrador con la página de consultas programadas.
 */
@Component
@ManagedBean(name = "calendarioBean")
@ApplicationScoped
@Getter @Setter
public class CalendarioBean {
    
    @Inject
    private ConsultaService consultaService;
    @Inject
    private UsuarioService usuarioService;
    private ScheduleModel modelo =  new DefaultScheduleModel();
    private String severTimeZone = ZoneId.systemDefault().toString();
    private Consulta consulta;
    private DefaultScheduleEvent<?> eventoSeleccionado = new DefaultScheduleEvent<>();
    private HashMap<DefaultScheduleEvent<?>, Consulta> eventosConsultas = new HashMap<>();
    private UploadedFile comprobantePago;
    private EstadoConsulta estadoConsulta;
    private String observacionesConsulta;

    /**
     * Metodo que selecciona un evento en la iteracion de las consultas en la base de datos
     * @param seleccionEvento El evento que ha seleccionado el administrador en el Schedule
     */
    public void onEventSelect(SelectEvent<DefaultScheduleEvent<?>> seleccionEvento) {
        eventoSeleccionado = seleccionEvento.getObject();
        consulta = eventosConsultas.get(eventoSeleccionado);
        estadoConsulta = consulta.getEstadoConsulta();
        observacionesConsulta = consulta.getObservaciones();
        //Falta obtener el comprobante de pago
        //Ver como pasar File a UploadedFile
    }

    /**
     * Guarda la consulta que se ha seleccionado y se actualiza dados us cambios.
     */
    public void guardarConsulta() {
        if (!consulta.getEstadoConsulta().equals(estadoConsulta)) {
            consulta.setEstadoConsulta(estadoConsulta);
            String color = colorConsulta(consulta.getEstadoConsulta());
            eventoSeleccionado.setBorderColor(color);
            eventoSeleccionado.setBorderColor(color);
            modelo.updateEvent(eventoSeleccionado);
        }
        //Falta guardar el comprobante de pago
        //Ver como pasar UploadedFile a File
        //O como guardar un UploadedFile
        consulta.setObservaciones(observacionesConsulta);
        consultaService.updateConsulta(consulta);
    }

    /**
     * Dado el estado de la consulta le da un color a esta
     * @param estadoConsulta Estado de la consulta que peude ser Agendado, Pagado o Atendido.
     * @return String con el hexadecimal del color que representa el estado de la consulta.
     */
    public String colorConsulta(EstadoConsulta estadoConsulta) {
        String color;
        switch (estadoConsulta) {
            case AGENDADA:
                color = "#FFFF00";
                break;
            case PAGADA:
                color = "#27AE60";
                break;
            case ATENDIDA:
                color = "#c2c2c2";
                break;
            default:
                color = "white";
                break;
        }
        return color;
    }

    /**
     * Con base en las consultas que hay agendadas las crea un evento para cada una y despues los agrega al modelo
     * del Schedule
     */
    public void crearEventos() {
        modelo.clear();
        eventosConsultas = new HashMap<>();
        DefaultScheduleEvent<?> evento;
        List<Consulta> consultas = consultaService.getAllConsultas();
        String color;
        for (Consulta c: consultas){
            color = colorConsulta(c.getEstadoConsulta());
            LocalDateTime fechaInicial = c.getId().getFecha().atTime(c.getId().getHora());
            LocalDateTime fechaFinal = fechaInicial.plusMinutes(ConsultaService.INTERVALO_MINUTOS);
            evento = DefaultScheduleEvent.builder()
                    .title(c.getUsuario().getNombre())
                    .startDate(fechaInicial)
                    .endDate(fechaFinal)
                    .description(c.getRazonConsulta())
                    .borderColor(color)
                    .backgroundColor(color)
                    .editable(false)
                    .overlapAllowed(false)
                    .build();
            modelo.addEvent(evento);
            eventosConsultas.put(evento, c);
        }
    }

    public void setEstadoConsulta(String estadoConsulta) {
        this.estadoConsulta = EstadoConsulta.valueOf(estadoConsulta);
    }

    public String getEstadoConsulta() {
        return String.valueOf(estadoConsulta);
    }
}
