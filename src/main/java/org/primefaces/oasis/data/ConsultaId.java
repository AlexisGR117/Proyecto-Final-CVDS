package org.primefaces.oasis.data;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

@Embeddable
@Getter @Setter @EqualsAndHashCode @ToString
public class ConsultaId implements Serializable {
    private LocalDate fecha;
    private LocalTime hora;

    public ConsultaId(){}

    public ConsultaId(LocalDate fecha, LocalTime hora) {
        this.fecha = fecha;
        this.hora = hora;
    }
}
