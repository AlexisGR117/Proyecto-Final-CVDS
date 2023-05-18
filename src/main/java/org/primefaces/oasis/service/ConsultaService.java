package org.primefaces.oasis.service;

import org.primefaces.oasis.data.Consulta;
import org.primefaces.oasis.data.ConsultaId;
import org.primefaces.oasis.data.Usuario;
import org.primefaces.oasis.exceptions.ConsultasException;
import org.primefaces.oasis.repository.ConsultaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

/**
 * Clase que se encarga de la logica de negocio de la aplicacion relacionada con las consultas.
 */
@Service
public class ConsultaService implements Serializable {

    public static final int LIMITE_INFERIOR = 7;
    public static final int LIMITE_SUPERIOR = 15;
    public static final int INTERVALO_MINUTOS = 60;
    public static final int DIAS_MAXIMO = 30;
    public static final int PRECIO = 150;
    public static final List<Integer> DIAS_DESHABILITADOS = Arrays.asList(0, 6);
    private final ConsultaRepository consultaRepository;
    @PersistenceContext
    EntityManager entityManager;

    /**
     * Constructor para objetos de clase ConsultaService.
     *
     * @param consultaRepository Repositorio que accede a la base de datos.
     */
    @Autowired
    public ConsultaService(ConsultaRepository consultaRepository) {
        this.consultaRepository = consultaRepository;
    }

    /**
     * Agrega un nueva consulta a la base de datos.
     *
     * @param consulta Consulta que se quiere agregar.
     * @return La consulta que se agregó a la base de datos.
     */
    public Consulta addConsulta(Consulta consulta) throws ConsultasException {
        if (consulta.getId().getFecha() == null) throw new ConsultasException(ConsultasException.CONSULTA_SIN_FECHA);
        return consultaRepository.save(consulta);
    }

    /**
     * Obtiene la consulta dada su id.
     *
     * @param consultaId Identificador de la consulta de tipo ConsultaId.
     * @return La consulta que tiene la identificacion dada.
     */
    public Optional<Consulta> getConsulta(ConsultaId consultaId) {
        return consultaRepository.findById(consultaId);
    }

    /**
     * Da todas las consultas que están en la base de datos.
     *
     * @return Lista con las consultas disponibles.
     */
    public List<Consulta> getAllConsultas() {
        return consultaRepository.findAll();
    }

    /**
     * Actualiza una consulta en la base de datos.
     *
     * @param consulta Consulta que se quiere actualizar.
     * @return La nueva consulta actualizada.
     */
    public Consulta updateConsulta(Consulta consulta) {
        if (consultaRepository.existsById(consulta.getId())) {
            return consultaRepository.save(consulta);
        }
        return null;
    }

    /**
     * Elimina una consulta de la base de datos.
     *
     * @param consultaId Identificador de la consulta.
     */
    public void deleteConsulta(ConsultaId consultaId) {
        consultaRepository.deleteById(consultaId);
    }

    /**
     * Da las consultas que se encuentran disponibles para una fecha dada.
     *
     * @param fecha es de tipo LocalDate y es la fecha que me ingresan que quieran consultar
     * @return un listado con las consultas de esa fecha
     */
    public List<String> getConsultasFecha(LocalDate fecha) throws ConsultasException {
        List<Consulta> consultas = consultaRepository.findByIdFecha(fecha);
        return validador(consultas);
    }

    /*
     * Verifica dentro de las consultas las que su Id su fecha sea la que estoy buscando
     * @param consultas Listado de todas las consultas
     * @param fecha LocalDate dada para comparar
     * @return Listado con las consultas con la fecha correcto.
     */
    private List<String> validador(List<Consulta> consultas) throws ConsultasException {
        List<String> horasNuevas = new ArrayList<>();
        for (Consulta i : consultas) {
            horasNuevas.add(i.getId().getHora().toString());
        }
        return horaSeteada(horasNuevas);
    }

    /*
     * Verifica que horas no han sido tomadas para mostrarlas como un nuevo posible registro.
     * @param valoresPosibles Las horas que ya se encuentran ocupadas.
     * @return Las horas que se pueden agendar consultas.
     * @throws ConsultasException LLENO_DE_CONSULTAS, si no hay horas disponibles para agendar una consulta.
     */
    private List<String> horaSeteada(List<String> valoresPosibles) throws ConsultasException {
        List<String> horas = new ArrayList<>();
        LocalTime clock = LocalTime.of(LIMITE_INFERIOR, 0, 0);
        int i = (LIMITE_INFERIOR * 60);
        while (i <= LIMITE_SUPERIOR * 60) {
            if (!valoresPosibles.contains(clock.toString())) {
                horas.add(clock.toString());
            }
            clock = clock.plusMinutes(INTERVALO_MINUTOS);
            i += INTERVALO_MINUTOS;
        }
        if (horas.isEmpty()) {
            throw new ConsultasException(ConsultasException.LLENO_DE_CONSULTAS);
        }
        return horas;
    }
    public void enviarCorreo(Usuario usuario, Consulta consulta){
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port","465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth",true);
        props.put("mail.smtp.port","587");
        Session session = Session.getDefaultInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication("sagomezab@gmail.com","byohzpofurqxlahn");
            }
        });
        try{
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("sagomezab@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(usuario.getEmail()));
            message.setSubject("Confirmacion de Cita");
            message.setText("Hola " + usuario.getNombre() + "\n" + "Este correo es para confirmarle que su cita quedo " +
                    "agendada para el dia " + consulta.getId().getFecha().toString() + " a la hora: " + consulta.getId().getHora().toString());
            Transport.send(message);
        }catch (MessagingException e){
            throw new RuntimeException(e);
        }
    }
}
