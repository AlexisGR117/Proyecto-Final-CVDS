package org.primefaces.oasis.service;

import org.primefaces.model.file.*;
import org.primefaces.oasis.data.Consulta;
import org.primefaces.oasis.data.ConsultaId;
import org.primefaces.oasis.exceptions.ConsultasException;
import org.primefaces.oasis.repository.ConsultaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Clase que se encarga de la logica de negocio de la aplicacion relacionada con las consultas.
 */
@Service
public class ConsultaService implements Serializable {

    @PersistenceContext
    EntityManager entityManager;

    public static final int LIMITE_INFERIOR = 7;
    public static final int LIMITE_SUPERIOR = 15;
    public static final int INTERVALO_MINUTOS = 60;
    public static final int DIAS_MAXIMO = 30;
    public static final int PRECIO = 150;
    public static final List<Integer> DIAS_DESHABILITADOS = Arrays.asList(0, 6);
    private final ConsultaRepository consultaRepository;

    /**
     * Constructor para objetos de clase ConsultaService.
     * @param consultaRepository Repositorio que accede a la base de datos.
     */
    @Autowired
    public ConsultaService(ConsultaRepository consultaRepository) {
        this.consultaRepository = consultaRepository;
    }

    /**
     * Agrega un nueva consulta a la base de datos.
     * @param consulta Consulta que se quiere agregar.
     * @return La consulta que se agregó a la base de datos.
     */
    public Consulta addConsulta(Consulta consulta) throws ConsultasException {
        if(consulta.getId().getFecha() == null) throw new ConsultasException(ConsultasException.CONSULTA_SIN_FECHA);
        return consultaRepository.save(consulta);
    }

    /**
     * Obtiene la consulta dada su id.
     * @param consultaId Identificador de la consulta de tipo ConsultaId.
     * @return La consulta que tiene la identificicaion dada.
     */
    public Optional<Consulta> getConsulta(ConsultaId consultaId) {
        return consultaRepository.findById(consultaId);
    }

    /**
     * Da todas las consultas que están en la base de datos.
     * @return Lista con las consultas disponibles.
     */
    public List<Consulta> getAllConsultas() {
        return consultaRepository.findAll();
    }

    /**
     * Actualiza una consulta en la base de datos.
     * @param consulta Consulta que se quiere actualizar.
     * @return La nueva consulta actualizada.
     */
    public Consulta updateConsulta(Consulta consulta){
        if(consultaRepository.existsById(consulta.getId())){
            return consultaRepository.save(consulta);
        }
        return null;
    }

    /**
     * Elimina una consulta de la base de datos.
     * @param consultaId Identificador de la consulta.
     */
    public void deleteConsulta(ConsultaId consultaId) {
        consultaRepository.deleteById(consultaId);
    }

    /**
     * Este metodo lo que hace es consulta sobre una fecha
     * @param fecha es de tipo LocalDate y es la fecha que me ingresan que quieran consultar
     * @return un listado con las consultas de esa fecha
     */
    public List<String> getConsultasFecha(LocalDate fecha) throws ConsultasException {
        List<Consulta> consultas = entityManager.createQuery("SELECT c FROM Consulta c Where c.id.fecha = :valName")
                .setParameter("valName", fecha)
                .getResultList();
        return validador(consultas);
    }

    /*
     * Valida dentro de las consultas las que su Id su fecha sea la que estoy buscando
     * @param consultas Listado de todas las consultas
     * @param fecha Fecha dada para comparar
     * @return Listado con las consultas con la fecha correcto.
     */
    private List<String> validador(List<Consulta> consultas) throws ConsultasException {
        List<String> horasNuevas = new ArrayList<>();
        for(Consulta i :  consultas){
            horasNuevas.add(i.getId().getHora().toString());
        }
        /*for (Consulta i : consultas){
            if(i.getId().getFecha().equals(fecha)) {
                horasNuevas.add(i.getId().getHora().toString());
            }
        }*/
        return horaSeteada(horasNuevas);
    }

    /*
     * Este metodo se encarga de verificar que horas no han sido tomadas para mostrarlas como un nuevo posible
     * registro
     * @param valoresPosibles
     * @return
     * @throws ConsultasException
     */
    private List<String> horaSeteada(List<String> valoresPosibles) throws ConsultasException {
        List<String> horas = new ArrayList<>();
        LocalTime clock = LocalTime.of(LIMITE_INFERIOR,0,0);
        int i = (LIMITE_INFERIOR * 60);
        while (i <= LIMITE_SUPERIOR * 60) {
            if(!valoresPosibles.contains(clock.toString())){
                horas.add(clock.toString());
            }
            clock = clock.plusMinutes(INTERVALO_MINUTOS);
            i += INTERVALO_MINUTOS;
        }
        if(horas.isEmpty()){
            throw new ConsultasException(ConsultasException.LLENO_DE_CONSULTAS);
        }
        return horas;
    }

    /**
     * Convirte un UploadFile a File
     * AUN NO SE HA TERMINADO
     * @param uploadedFile Archivo que se quiere convertir a File
     * @return El archivo en tipo File.
     * @throws ConsultasException ERROR_ARCHIVO, si al convertir archivo hay un error.
     */
    public File convertirFile(UploadedFile uploadedFile) throws ConsultasException {
        try {
            byte[] bytes = uploadedFile.getContent();
            String filename = uploadedFile.getFileName();
            File file = new File(filename);
            try (FileOutputStream fos = new FileOutputStream(file)) {
                fos.write(bytes);
            }
            return file;
        } catch (Exception e) {
            throw new ConsultasException(ConsultasException.ERROR_ARCHIVO);
        }
    }
}
