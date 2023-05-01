package org.primefaces.oasis.data;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter @Setter @EqualsAndHashCode @ToString
public class ConsultaId implements Serializable {
    private String ano;
    private String mes;
    private String dia;
    private String hora;

    public ConsultaId(){}

    public ConsultaId(String ano, String mes, String dia, String hora) {
        this.ano = ano;
        this.mes = mes;
        this.dia = dia;
        this.hora = hora;
    }
}
