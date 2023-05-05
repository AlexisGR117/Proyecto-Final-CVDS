package org.primefaces.oasis.form;

import javax.faces.bean.ManagedBean;
import javax.inject.Inject;
import javax.annotation.PostConstruct;

import org.primefaces.oasis.service.ConsultaService;
import org.primefaces.oasis.service.UsuarioService;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.ApplicationScope;

import lombok.EqualsAndHashCode;    
import lombok.Getter;
import lombok.Setter;

import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;
import org.primefaces.oasis.data.Consulta;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;

@Component
@ManagedBean(name = "calendarioBean")
@ApplicationScope
@Getter @Setter
public class CalendarioBean {
    
    @Inject
    private ConsultaService consultaService;
    private ScheduleModel modelo;
    private String severTimeZone = ZoneId.systemDefault().toString();
    private Consulta consulta;
    private ScheduleEvent<?> evento = new DefaultScheduleEvent<>();
    private HashMap<DefaultScheduleEvent<?>, Consulta> eventosConsultas = new HashMap<DefaultScheduleEvent<?>, Consulta>();


    /**
     * Metodo que realiza una iteracion en las consultas de la base de datos y realiza una asignación a los eventos
     * con sus atributos correspondientes
     */
    @PostConstruct
    public void init(){
        List<Consulta> consultas = consultaService.getAllConsultas();
        DefaultScheduleEvent<?> evento;
        for (Consulta c: consultas){
            LocalDateTime fechaInicial = c.getId().getFecha().atTime(c.getId().getHora());
            LocalDateTime fechaFinal = fechaInicial.plusMinutes(ConsultaService.intervaloMinutos);
            evento = DefaultScheduleEvent.builder()
                .title(c.getUsuario().getNombreUsuario())
                .startDate(fechaInicial)
                .endDate(fechaFinal)
                .description(c.getRazonConsulta())
                .borderColor(c.getEstadoConsulta().equals("Agendado") ? "#ff200" : c.getEstadoConsulta().equals("Pagado") ? "#27AE60" : c.getEstadoConsulta().equals("Atendido") ? "#c2c2c2" : "white")
                .build();
            modelo.addEvent(evento);
            eventosConsultas.put(evento, c);
        }
    }

    /**
     * Metodo que selecciona un evento en la iteracion de las consultas en la base de datos
     * @param seleccionEvento
     */
    public void onEventSelect(SelectEvent<ScheduleEvent<?>> seleccionEvento){
        evento = seleccionEvento.getObject();
        consulta = eventosConsultas.get(evento);
    }



    
    
}
