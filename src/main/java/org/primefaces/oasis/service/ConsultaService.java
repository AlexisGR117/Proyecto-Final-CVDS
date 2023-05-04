package org.primefaces.oasis.service;

import org.primefaces.oasis.data.Consulta;
import org.primefaces.oasis.data.ConsultaId;
import org.primefaces.oasis.repository.ConsultaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.swing.text.html.parser.Entity;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ConsultaService implements Serializable {
    private static final int limiteInferior = 7;
    private static final int limiteSuperior = 15;
    private static final int intervaloMinutos = 30;
    private final ConsultaRepository consultaRepository;
    @Autowired
    public ConsultaService(ConsultaRepository consultaRepository){
        this.consultaRepository = consultaRepository;
    }
    public Consulta addConsulta(Consulta consulta){
        return consultaRepository.save(consulta);
    }
    public Optional<Consulta> getConsulta(ConsultaId consultaId){
        return consultaRepository.findById(consultaId);
    }
    public List<Consulta> getAllConsultas(){
        return consultaRepository.findAll();
    }

    public Consulta updateConsulta(Consulta consulta){
        if(consultaRepository.existsById(consulta.getId())){
            return consultaRepository.save(consulta);
        }
        return null;
    }
    public void deleteConsulta(ConsultaId consultaId){
        consultaRepository.deleteById(consultaId);
    }

    /**
     * Este metodo lo que hace es consulta sobre una fecha
     * @param fecha es de tipo LocalDate y es la fecha que me ingresan que quieran consultar
     * @return un listado con las consultas de esa fecha
     */
    public List<String> getConsultasFecha(LocalDate fecha){
        List<Consulta> consultas = getAllConsultas();
        return(validator(consultas, fecha));
    }

    /**
     * Valida dentro de las consultas las que su Id su fecha sea la que estoy buscando
     * @param consultas Listado de todas las consultas
     * @param fecha Fecha dada para comparar
     * @return Listado con las consultas con la fecha correcto.
     */
    private List<String> validator(List<Consulta> consultas, LocalDate fecha){
        List<String> horasNuevas = new ArrayList<>();
        for (Consulta i : consultas){
            if(i.getId().getFecha().equals(fecha)) {
                horasNuevas.add(i.getId().getHora().toString());
            }
        }
        List<String> horasPosibles = horaSetter(horasNuevas);
        return horasPosibles;
    }
    private List<String> horaSetter(List<String> valoresPosibles){
        List<String> horas = new ArrayList<>();
        LocalTime clock = LocalTime.of(limiteInferior,0,0);
        horas.add(clock.toString());
        int i = (limiteInferior * 100) + intervaloMinutos;
        while (i < limiteSuperior*100){
            clock = clock.plusMinutes(intervaloMinutos);
            if(!valoresPosibles.contains(clock.toString())){
                horas.add(clock.toString());
            }
            i += intervaloMinutos;
        }
        return horas;
    }
}
