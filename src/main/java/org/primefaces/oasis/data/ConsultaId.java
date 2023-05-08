package org.primefaces.oasis.data;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Identificador de la consulta es compuesta por la fecha y la hora de la consulta.
 */
@Embeddable
@Getter @Setter @EqualsAndHashCode @ToString
public class ConsultaId implements Serializable {
    private LocalDate fecha;
    private LocalTime hora;

    /**
     * Constructor vacio para objetos de clase ConsultaID.
     */
    public ConsultaId(){}

    /**
     * Constructor para objetos de clase ConsultaID.
     * @param fecha Fecha de la consulta.
     * @param hora Hora de la consulta.
     */
    public ConsultaId(LocalDate fecha, LocalTime hora) {
        this.fecha = fecha;
        this.hora = hora;
    }
}
