package org.primefaces.oasis.service;

import org.primefaces.oasis.data.Consulta;
import org.primefaces.oasis.data.ConsultaId;
import org.primefaces.oasis.exceptions.ConsultasException;
import org.primefaces.oasis.repository.ConsultaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class ConsultaService implements Serializable {
    public static final int LIMITE_INFERIOR = 7;
    public static final int LIMITE_SUPERIOR = 15;
    public static final int INTERVALO_MINUTOS = 60;
    public static final int DIAS_MAXIMO = 30;
    public static final int PRECIO = 150;
    public static final List<Integer> DIAS_DESHABILITADOS = Arrays.asList(0, 6);
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
    public List<String> getConsultasFecha(LocalDate fecha) throws ConsultasException {
        List<Consulta> consultas = getAllConsultas();
        return(validador(consultas, fecha));
    }

    /**
     * Valida dentro de las consultas las que su Id su fecha sea la que estoy buscando
     * @param consultas Listado de todas las consultas
     * @param fecha Fecha dada para comparar
     * @return Listado con las consultas con la fecha correcto.
     */
    private List<String> validador(List<Consulta> consultas, LocalDate fecha) throws ConsultasException {
        List<String> horasNuevas = new ArrayList<>();
        for (Consulta i : consultas){
            if(i.getId().getFecha().equals(fecha)) {
                horasNuevas.add(i.getId().getHora().toString());
            }
        }
        return horaSeteada(horasNuevas);
    }

    /**
     * Este metodo se encarga de verificar que horas no han sido tomadas para mostrarlas como un nuevo posible regisatro
     * @param valoresPosibles
     * @return
     * @throws ConsultasException
     */
    private List<String> horaSeteada(List<String> valoresPosibles) throws ConsultasException {
        List<String> horas = new ArrayList<>();
        LocalTime clock = LocalTime.of(LIMITE_INFERIOR,0,0);
        horas.add(clock.toString());
        int i = (LIMITE_INFERIOR * 100) + INTERVALO_MINUTOS;
        while (i < LIMITE_SUPERIOR *100){
            clock = clock.plusMinutes(INTERVALO_MINUTOS);
            if(!valoresPosibles.contains(clock.toString())){
                horas.add(clock.toString());
            }
            i += INTERVALO_MINUTOS;
        }
        if(horas.isEmpty()){
            throw new ConsultasException(ConsultasException.LLENO_DE_CONSULTAS);
        }
        return horas;
    }
}
